Shields4j |Master| |MavenCentral| |DocumentationStatus| |AlertStatus| |Coverage|
=======================================

.. |Master| image:: https://github.com/touchbit/shields4j/workflows/Master/badge.svg?style=plastic
    :target: https://github.com/touchbit/shields4j/actions?query=workflow%3Amaster

.. |MavenCentral| image:: https://maven-badges.herokuapp.com/maven-central/org.touchbit.shields4j/shields4j-parent/badge.svg
    :target: https://mvnrepository.com/artifact/org.touchbit.shields4j

.. |DocumentationStatus| image:: https://readthedocs.org/projects/shields4j/badge/?version=master
    :target: https://shields4j.readthedocs.io

.. |AlertStatus| image:: https://sonarcloud.io/api/project_badges/measure?project=org.touchbit.shields4j%3Ashields4j-parent&metric=alert_status
    :target: https://sonarcloud.io/dashboard?id=org.touchbit.shields4j%3Ashields4j-parent

.. |Coverage| image:: https://sonarcloud.io/api/project_badges/measure?project=org.touchbit.shields4j%3Ashields4j-parent&metric=coverage&blinking=true
    :target: https://sonarcloud.io/component_measures?id=org.touchbit.shields4j%3Ashields4j-parent&metric=coverage

Lightweight java HTTP-client for the `shields.io`_ service.

.. _shields.io: https://shields.io/

Full documentation is available on the `shields4j.readthedocs.io`_

.. _shields4j.readthedocs.io: https://shields4j.readthedocs.io/

Briefly
-------

Java client
^^^^^^^^^^^

Add dependency

.. code:: xml

    <dependency>
        <groupId>org.touchbit.shields4j</groupId>
        <artifactId>client</artifactId>
        <version>${shields4j.version}</version>
    </dependency>

If you need to get Shields4j versions that are not uploaded to maven central (snapshot/staging), add the Sonatype repository to your pom.xml

.. code:: xml

    <repositories>
        <repository>
            <id>SonatypeNexus</id>
            <url>https://oss.sonatype.org/content/groups/staging/</url>
        </repository>
    </repositories>

Minimal |minimal|

.. |minimal| image:: .docs/img/minimal.svg

.. code:: java

    class Example {
        public static void main (String[] arg) {
            new ShieldsIO()
                .withLabel("Shields4J")
                .withMessage("1.0")
                .writeShieldToFile("minimal.svg");
        }
    }

With logo and color background |withlogo|

.. |withlogo| image:: .docs/img/with-logo.svg

.. code:: java

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

With custom logo and color background |withcustomlogo|

.. |withcustomlogo| image:: .docs/img/with-custom-logo.svg

.. code:: java

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

TestNG listener
^^^^^^^^^^^^^^^

Add dependency

.. code:: xml

    <dependency>
        <groupId>org.touchbit.shields4j</groupId>
        <artifactId>testng</artifactId>
        <version>${shields4j.version}</version>
    </dependency>

Add IShieldsListener to testNG |iteststotal| |itestssuccesspercent|

.. |iteststotal| image:: .docs/img/TestNG-iTests-total.svg

.. |itestssuccesspercent| image:: .docs/img/TestNG-iTests-success-percent.svg

.. code:: java

    class Example {
        public static void main (String[] arg) {
             TestNG testNG = new TestNG();
             testNG.addListener(new IShieldsListener());
        }
    }

or add IShieldsListener in your testng.xml file

.. code:: xml

    <suite>
      <listeners>
        <listener class-name="org.touchbit.shields4j.testng.IShieldsListener" />
      </listeners>
    </suite>

To customize the prefixes, create your own listener inherited from IShieldsListener |integrationtesttotal| |integrationtestsuccesspercent|

.. |integrationtesttotal| image:: .docs/img/testng-Integration-test-total.svg

.. |integrationtestsuccesspercent| image:: .docs/img/testng-Integration-test-success-percent.svg

.. code:: java

    public class ShieldsListener extends IShieldsListener {
        public ShieldsListener() {
            withLabelPefix("Integration test");
            withFilePefix("testng");
        }
    }
