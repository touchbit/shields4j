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
