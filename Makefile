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

build: clean
	mvn package

test: clean
	mvn test

deploy: clean
	mvn deploy -DskipTests=true

deploy-st: clean
	mvn deploy -Dperform-sonatype-release

version:
	mvn versions:set -DnewVersion=${VERSION}
	mvn package -DskipTests

ver: version

clean-doc:
	rm -rf ./site

build-doc: clean-doc
	sphinx-build -W -b html ./docs ./site

build-doc-image:
	docker build --no-cache -t shields4j/doc:${VERSION} -f .docs/Dockerfile .

run-doc-image: build-doc-image
	docker run -p 8080:80 shields4j/doc:${VERSION}