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