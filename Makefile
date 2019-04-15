ifneq ($(MAKECMDGOALS),$(findstring $(MAKECMDGOALS),build-doc-image run-doc-image version))
    VERSION := $(wordlist 2,$(words $(MAKECMDGOALS)),$(MAKECMDGOALS))
else
	ifneq (version,$(firstword $(MAKECMDGOALS)))
		VERSION := latest
	endif
endif
$(eval $(VERSION):;@:)

clean:
	mvn clean

# do not use -Dmaven.test.skip=true
install: clean
	mvn install -DskipTests

test: clean
	mvn test

# do not use -Dmaven.test.skip=true
deploy: clean
	mvn deploy -Dperform-touchbit-release

deploy-st: clean
	mvn deploy -Dperform-sonatype-release

version:
	mvn versions:set -DnewVersion=${VERSION}
	mvn install -DskipTests

ver: version

clean-doc:
	rm -rf ./site

build-doc: clean-doc
	sphinx-build -W -b html ./docs ./site

build-doc-image:
	docker build --no-cache -t shields4j/doc:${VERSION} -f ./.indirect/docs/Dockerfile .

run-doc-image: build-doc-image
	docker run -p 8080:80 shields4j/doc:${VERSION}