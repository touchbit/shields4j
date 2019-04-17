# Shields4J [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.touchbit.utils/shields4j/badge.svg?style=plastic)](https://mvnrepository.com/artifact/org.touchbit.utils) [![GitLab](https://img.shields.io/badge/Source-GitLab-blue.svg?style=plastic)](https://gitlab.com/TouchBIT/shields4j)

[![master](https://gitlab.com/TouchBIT/shields4j/badges/master/build.svg)](https://gitlab.com/TouchBIT/shields4j/pipelines) [![Documentation Status](https://readthedocs.org/projects/shields4j/badge/?version=latest)](https://shields4j.readthedocs.io/en/latest/?badge=latest)
 [![alert_status](https://touchbit.org/sonar/api/project_badges/measure?project=org.touchbit.shields4j%3Ashields4j-parent&metric=alert_status)](https://touchbit.org/sonar/dashboard?id=org.touchbit.shields4j%3Ashields4j-parent)  [![coverage](https://touchbit.org/sonar/api/badges/measure?key=org.touchbit.shields4j%3Ashields4j-parent&metric=coverage&blinking=true)](https://touchbit.org/sonar/component_measures?id=org.touchbit.shields4j%3Ashields4j-parent&metric=coverage) [![tests](https://touchbit.org/sonar/api/badges/measure?key=org.touchbit.shields4j%3Ashields4j-parent&metric=tests&blinking=true)](https://touchbit.org/sonar/component_measures?id=org.touchbit.shields4j%3Ashields4j-parent&metric=ncloc) [![test_success_density](https://touchbit.org/sonar/api/badges/measure?key=org.touchbit.shields4j%3Ashields4j-parent&blinking=true&metric=test_success_density)](https://touchbit.org/sonar/component_measures?id=org.touchbit.shields4j%3Ashields4j-parent&metric=tests)

**Lightweight java HTTP-client for the [shields.io](https://shields.io/) service.**

Full documentation is available on the [shields4j.readthedocs.io](https://readthedocs.org/projects/shields4j/)

## Briefly
### Client

Add dependency
```xml
<dependency>
   <groupId>org.touchbit.shields4j</groupId>
   <artifactId>client</artifactId>
   <version>${shields4j.version}</version>
</dependency>
```   
Minimal ![](./docs/img/minimal.svg)   
```java
class Example {
    public static void main (String[] arg) {
        new ShieldsIO()
            .withLabel("Shields4J")
            .withMessage("1.0")
            .writeShieldToFile("minimal.svg");
    }
}
```   
With logo and color background ![](./docs/img/with-logo.svg)   
```java
class Example {
    public static void main (String[] arg) {
        new ShieldsIO()
            .withLabel("Shields4J")
            .withLabelColor(Color.BLUE)
            .withMessage("with Raspberry PI logo")
            .withMessageColor(Color.GREEN)
            .withStyle(Style.POPOUT)
            .withLogo(Logo.RASPBERRY_PI)
            .withLogoColor(Color.RED)
            .withLogoWidth(30)
            .writeShieldToFile("with-logo.svg");
    }
}
```   
With custom logo and color background ![](./docs/img/with-custom-logo.svg)   
```java
class Example {
    public static void main (String[] arg) {
         new ShieldsIO()
            .withLabel("Shields4J")
            .withLabelColor(Color.BLUE)
            .withMessage("with custom logo")
            .withMessageColor(Color.GREEN)
            .withStyle(Style.POPOUT)
            .withBase64Logo(new File("docs/img/status_success.svg"))
            .writeShieldToFile("with-custom-logo.svg");
    }
}
```  

### TestNG
Add dependency   
```xml
<dependency>
   <groupId>org.touchbit.shields4j</groupId>
   <artifactId>testng</artifactId>
   <version>${shields4j.version}</version>
</dependency>
```   
Add IShieldsListener to testNG   
![](./docs/img/TestNG-iTests-total.svg) ![](./docs/img/TestNG-iTests-success-percent.svg)   
```java
class Example {
    public static void main (String[] arg) {
         TestNG testNG = new TestNG();
         testNG.addListener(new IShieldsListener());
    }
}
```   
or add IShieldsListener in your `testng.xml` file   
```xml
<suite>
  <listeners>
    <listener class-name="org.touchbit.shields4j.testng.IShieldsListener" />
  </listeners>
</suite>
```   
To customize the prefixes, create your own listener inherited from ShieldsListener   
![](./docs/img/testng-Integration-test-total.svg) ![](./docs/img/testng-Integration-test-success-percent.svg)   
```java
public class ShieldsListener extends IShieldsListener {
    public ShieldsListener() {
        withLabelPefix("Integration test");
        withFilePefix("testng");
    }
}
```   
