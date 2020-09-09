Shields4j |MavenCentral|
========================

.. |MavenCentral| image:: https://maven-badges.herokuapp.com/maven-central/org.touchbit.shields4j/shields4j-parent/badge.svg?style=plastic
    :target: https://mvnrepository.com/artifact/org.touchbit.shields4j

Lightweight java HTTP-client for the `shields.io`_ service.

.. _shields.io: https://shields.io/

Java client
===========

Add dependency

.. code:: xml

    <dependency>
        <groupId>org.touchbit.shields4j</groupId>
        <artifactId>client</artifactId>
        <version>${shields4j.version}</version>
    </dependency>

Minimal |minimal|

.. |minimal| image:: img/minimal.svg

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

.. |withlogo| image:: img/with-logo.svg

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

.. |withcustomlogo| image:: img/with-custom-logo.svg

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
===============

Add dependency

.. code:: xml

    <dependency>
        <groupId>org.touchbit.shields4j</groupId>
        <artifactId>testng</artifactId>
        <version>${shields4j.version}</version>
    </dependency>

Add IShieldsListener to testNG |iteststotal| |itestssuccesspercent|

.. |iteststotal| image:: img/TestNG-iTests-total.svg

.. |itestssuccesspercent| image:: img/TestNG-iTests-success-percent.svg

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

To customize the prefixes, create your own listener inherited from ShieldsListener |integrationtesttotal| |integrationtestsuccesspercent|

.. |integrationtesttotal| image:: img/testng-Integration-test-total.svg

.. |integrationtestsuccesspercent| image:: img/testng-Integration-test-success-percent.svg

.. code:: java

    public class ShieldsListener extends IShieldsListener {
        public ShieldsListener() {
            withLabelPefix("Integration test");
            withFilePefix("testng");
        }
    }


MIT License
===========

* Copyright (c) 2020 TouchBIT.
* Copyright (c) 2020 Oleg Shaburov.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
