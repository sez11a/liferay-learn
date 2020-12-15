# Installing on WildFly

Installing on WildFly requires deploying dependencies, modifying scripts, modifying config `xml` files, and deploying the DXP WAR file. In addition, make the optional database and mail server configurations to optimize the DXP instance.

Liferay DXP requires Java JDK 8 or 11. See [the compatibility matrix](https://help.liferay.com/hc/en-us/articles/360049238151) for further information.

Download these files from the [Help Center](https://customer.liferay.com/downloads) (subscription) or from [Liferay Community Downloads](https://www.liferay.com/downloads-community). Administrators must download the following:

* DXP WAR file
* Dependencies ZIP file
* OSGi Dependencies ZIP file

Before proceeding, you should understand the difference between [*Liferay Home*](../../reference/liferay-home.md) and `$WILDFLY_HOME` because they are referenced below as shorthand:

* `Liferay.home` is the folder containing the WildFly server folder. After installing and deploying DXP, the Liferay Home folder contains the WildFly server folder as well as `data`, `deploy`, `logs`, and `osgi` folders.

* `$WILDFLY_HOME` refers to the WildFly server folder. It is usually named `wildfly-[version]`.

Installing Liferay DXP on WildFly requires the following steps:

1. [Installing dependencies to the application server](#installing-dependencies)
1. [Configuring the application server for DXP](#configuring-wildfly)
1. [Connect to a Database](#connect-to-a-database)
1. [Connect to a Mail Server](#connect-to-a-mail-server)
1. [Deploying the DXP WAR file to the application server](#deploying-dxp)

## Installing Dependencies

1. Create the folder `$WILDFLY_HOME/modules/com/liferay/portal/main` if it does not already exist and extract the Dependencies ZIP JARs here.
1. Download a database driver `.jar` file and copy it into the same folder. For a list of supported databases, see Liferay's [Support Matrix](https://help.liferay.com/hc/en-us/articles/360049238151).
1. Create the file `module.xml` in the `$WILDFLY_HOME/modules/com/liferay/portal/main` folder. In the file, declare the portal module and all of its required resources and dependencies:

    ```xml
    <?xml version="1.0"?>

    <module xmlns="urn:jboss:module:1.0" name="com.liferay.portal">
        <resources>
            <resource-root path="[place your database vendor's JAR file name here]" />
            <resource-root path="[place a Liferay dependencies ZIP JAR file name here]" />
            <!-- Add a resource-root element for each Liferay dependencies ZIP JAR -->
        </resources>
        <dependencies>
            <module name="javax.api" />
            <module name="javax.mail.api" />
            <module name="javax.servlet.api" />
            <module name="javax.servlet.jsp.api" />
            <module name="javax.transaction.api" />
        </dependencies>
    </module>
    ```

    Replace `[place your database vendor's JAR file name here]` with the driver JAR for your database.

    For each JAR in the Liferay dependencies ZIP, add a `resource-root` element with its `path` attribute set to the JAR name. For example, add a `resource-root` element like this for the `com.liferay.petra.concurrent.jar` file:

    ```xml
    <resource-root path="com.liferay.petra.concurrent.jar" />
    ```

1. Create an `osgi` folder in your [Liferay Home](../../reference/liferay-home.md) folder. Extract the OSGi Dependencies ZIP file that you downloaded into the `[Liferay Home]/osgi` folder.

    The `osgi` folder provides the necessary modules for DXP's OSGi runtime.

**Checkpoint:**

1. The contents of the Dependencies zip have been placed in the `$WILDFLY_HOME/modules/com/liferay/portal/main` folder:
1. Your database vendor's JDBC driver has been placed in `$WILDFLY_HOME/modules/com/liferay/portal/main` folder and listed as a dependency.
1. The `module.xml` has listed all JARs in the `<resource-root>` elements.
1. The OSGi dependencies have been unzipped in the `osgi` folder located inside the `${Liferay.home}` folder.

### Running DXP on WildFly in Standalone Mode vs. Domain Mode

WildFly can be launched in either *standalone* mode or *domain* mode. Domain mode allows multiple application server instances to be managed from a single control point. A collection of such application servers is known as a *domain*. For more information on standalone mode vs. domain mode, please refer to the section on this topic in the [WildFly Admin Guide](https://docs.jboss.org/author/display/WFLY/Admin+Guide#AdminGuide-Operatingmodes).
DXP fully supports WildFly in standalone mode but not in domain mode.

Administrators can run DXP on WildFly in domain mode, but this method is not fully supported. In particular, DXP's auto-deploy does not work with a managed deployment, since WildFly manages the content of a managed deployment by copying files (exploded or non-exploded). This prevents JSP hooks and Ext plugins from working as intended. For example, JSP hooks don't work on WildFly running in managed domain mode, since DXP's JSP override mechanism relies on the application server. Since JSP hooks and Ext plugins are deprecated, however, you may not be using them.

The command line interface is recommended for domain mode deployments.

```note::
   This does not prevent DXP from running in a clustered environment on multiple WildFly servers. Administrators can set up a cluster of DXP instances running on WildFly servers running in standalone mode. Please refer to the `DXP clustering articles <../../setting-up-liferay-dxp/clustering-for-high-availability/clustering-for-high-availability.md>`_ for more information.
```

## Configuring WildFly

Configuring WildFly to run DXP includes these things:

* Setting environment variables
* Setting properties and descriptors
* Removing unnecessary configurations

Make the following modifications to `$WILDFLY_HOME/standalone/configuration/standalone.xml`:

1. In the `<jsp-config>` tag, set the Java VM compatibility for Liferay source and class files. They are compatible with Java 8 by default.

    ```xml
    <jsp-config development="true" source-vm="1.8" target-vm="1.8" />
    ```

1. Locate the closing `</extensions>` tag. Directly beneath that tag, insert the following system properties:

    ```xml
    <system-properties>
        <property name="org.apache.catalina.connector.URI_ENCODING" value="UTF-8" />
        <property name="org.apache.catalina.connector.USE_BODY_ENCODING_FOR_QUERY_STRING" value="true" />
    </system-properties>
    ```

1. Add the following `<filter-spec>` tag within the `<console-handler>` tag which is directly below the `<level name="INFO"/>` tag:

    ```xml
    <filter-spec value="not(any(match(&quot;WFLYSRV0059&quot;),match(&quot;WFLYEE0007&quot;)))" />
    ```

1. Add a timeout for the deployment scanner by setting `deployment-timeout="600"` as seen in the excerpt below.

    ```xml
    <subsystem xmlns="urn:jboss:domain:deployment-scanner:2.0">
        <deployment-scanner deployment-timeout="600" path="deployments" relative-to="jboss.server.base.dir" scan-interval="5000" runtime-failure-causes-rollback="${jboss.deployment.scanner.rollback.on.failure:false}"/>
    </subsystem>
    ```

1. Remove the welcome content code snippets:

    ```xml
    <location name="/" handler="welcome-content"/>
    ```

    and

    ```xml
    <handlers>
        <file name="welcome-content" path="${jboss.home.dir}/welcome-content"/>
    </handlers>
    ```

**Checkpoint:**

Before continuing, verify the following properties have been set in the `standalone.xml` file:

1. The new `<system-property>` is added.
1. The new `<filter-spec>` is added.
1. The `<deployment-timeout>` is set to `600`.
1. The new `<security-domain>` is created.
1. Welcome content is removed.

Next, configure the JVM and startup scripts:

In the `$WILDFLY_HOME/bin/` folder, open the standalone domain's configuration script file `standalone.conf` (`standalone.conf.bat` on Windows):

* Set the file encoding to `UTF-8`
* Set the user time zone to `GMT`
* Set the preferred protocol stack
* Increase the default amount of memory available.

```important::
   For DXP to work properly, the application server JVM must use the ``GMT`` time zone and ``UTF-8`` file encoding.
```

Make the following edits as applicable for the respective operating system:

**Windows:**

1. Comment out the initial `JAVA_OPTS` assignment:

    ```bash
    rem set "JAVA_OPTS=-Xms64M -Xmx512M -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=2560m"
    ```

1. Add the following `JAVA_OPTS` assignment one line above the `:JAVA_OPTS_SET` line found at end of the file:

    ```bash
    set "JAVA_OPTS=%JAVA_OPTS% -Dfile.encoding=UTF-8 -Djava.net.preferIPv4Stack=true -Djboss.as.management.blocking.timeout=480 -Duser.timezone=GMT -Xms2560m -Xmx2560m -XX:MaxMetaspaceSize=512m -XX:MetaspaceSize=200m"
    ```

**Unix:**

1. Below the `if [ "x$JAVA_OPTS" = "x" ];` statement, replace this `JAVA_OPTS` statement:

    ```bash
    JAVA_OPTS="-Xms64m -Xmx512m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -Djava.net.preferIPv4Stack=true"
    ```

    with this:

    ```bash
    JAVA_OPTS="-Djava.net.preferIPv4Stack=true"
    ```

1. Add the following statement to the bottom of the file:

    ```bash
    JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=UTF-8 -Djava.net.preferIPv4Stack=true -Djboss.as.management.blocking.timeout=480 -Duser.timezone=GMT -Xms2560m -Xmx2560m -XX:MaxMetaspaceSize=512m -XX:MetaspaceSize=200m"
    ```

This sets the file encoding to UTF-8, prefers an IPv4 stack over IPv6, sets the time zone to GMT, gives the JVM 2GB of RAM, and limits Metaspace to 512MB.

On JDK 11, add this JVM argument to display four-digit years.

```bash
-Djava.locale.providers=JRE,COMPAT,CLDR
```

After installation, tune the system (including these JVM options) for performance.

If using the IBM JDK with the WildFly server, complete the following additional steps:

1. Navigate to the `$WILDFLY_HOME/modules/com/liferay/portal/main/module.xml` file and insert the following dependency within the `<dependencies>` element:

    `<module name="ibm.jdk" />`

1. Navigate to the `$WILDFLY_HOME/modules/system/layers/base/sun/jdk/main/module.xml` file and insert the following path names inside the `<paths>...</paths>` element:

```xml
     <path name="com/sun/crypto" />
     <path name="com/sun/crypto/provider" />
     <path name="com/sun/org/apache/xml/internal/resolver" />
     <path name="com/sun/org/apache/xml/internal/resolver/tools" />
```

The added paths resolve issues with deployment exceptions and image uploading problems.

**Checkpoint:**

1. The file encoding, user time-zone, preferred protocol stack have been set in the `JAVA_OPTS` in the `standalone.conf.bat` file.
1. The default amount of memory available has been increased.

The prescribed script modifications are now complete for the DXP installation on WildFly.

## Connect to a Database

The easiest way to handle database configuration is to let DXP manage the data source. Use [Basic Configuration](../../../getting-started/using-the-setup-wizard.md) to configure DXP's built-in data source. If using the built-in data source, skip this section.

If using WildFly to manage the data source, follow these steps:

1. Add the data source inside the `$WILDFLY_HOME/standalone/configuration/standalone.xml` file's `<datasources>` element:

    ```xml
    <datasource jndi-name="java:jboss/datasources/ExampleDS" pool-name="ExampleDS" enabled="true" jta="true" use-java-context="true" use-ccm="true">
        <connection-url>[place the URL to your database here]</connection-url>
        <driver>[place your driver name here]</driver>
        <security>
            <user-name>[place your user name here]</user-name>
            <password>[place your password here]</password>
        </security>
    </datasource>
    ```

    Make sure to replace the database URL, user name, and password with the appropriate values.

    ```note::
       If the data source ``jndi-name`` must be changed, edit the ``datasource`` element in the ``<default-bindings>`` tag.
    ```

1. Add the driver to the `standalone.xml` file's `<drivers>` element also found within the `<datasources>` element.

    ```xml
    <drivers>
        <driver name="[name of database driver]" module="com.liferay.portal">
            <driver-class>[JDBC driver class]</driver-class>
        </driver>
    </drivers>
    ```

    A final data sources subsystem that uses MySQL should look like this:

    ```xml
    <subsystem xmlns="urn:jboss:domain:datasources:1.0">
        <datasources>
            <datasource jndi-name="java:jboss/datasources/ExampleDS" pool-name="ExampleDS" enabled="true" jta="true" use-java-context="true" use-ccm="true">
                <connection-url>jdbc:mysql://localhost/lportal</connection-url>
                <driver>mysql</driver>
                <security>
                    <user-name>root</user-name>
                    <password>root</password>
                </security>
            </datasource>
            <drivers>
                <driver name="mysql" module="com.liferay.portal">
                    <driver-class>com.mysql.cj.jdbc.Driver</driver-class>
                </driver>
            </drivers>
        </datasources>
    </subsystem>
    ```

1. In a [`portal-ext.properties`](../../reference/portal-properties.md) file in the Liferay Home folder, specify the data source:

    ```properties
    jdbc.default.jndi.name=java:jboss/datasources/ExampleDS
    ```

The data source is now configured and ready to go.

## Connect to a Mail Server

As with database configuration, the easiest way to configure mail is to let DXP handle the mail session. If you want to use DXP's built-in mail session, skip this section and [configure the mail session](../../setting-up-liferay-dxp/configuring-mail/connecting-to-a-mail-server.md) in the Control Panel.

If you want to manage your mail session with WildFly, follow these steps:

1. Specify your mail subsystem in the `$WILDFLY_HOME/standalone/configuration/standalone.xml` file like this:

    ```xml
    <subsystem xmlns="urn:jboss:domain:mail:3.0">
        <mail-session jndi-name="java:jboss/mail/MailSession" name="mail-smtp">
            <smtp-server ssl="true" outbound-socket-binding-ref="mail-smtp" username="USERNAME" password="PASSWORD"/>
       </mail-session>
    </subsystem>
    ...
    <socket-binding-group name="standard-sockets" default-interface="public" port-offset="${jboss.socket.binding.port-offset:0}">
    ...
    <outbound-socket-binding name="mail-smtp">
            <remote-destination host="[place SMTP host here]" port="[place SMTP port here]"/>
        </outbound-socket-binding>
    </socket-binding-group>
    ```

1. In the [`portal-ext.properties`](../../reference/portal-properties.md) file in Liferay Home, reference the mail session:

    ```properties
    mail.session.jndi.name=java:jboss/mail/MailSession
    ```

## Deploying DXP

1. If the folder `$WILDFLY_HOME/standalone/deployments/ROOT.war` already exists in the WildFly installation, delete all of its subfolders and files. Otherwise, create a new folder called `$WILDFLY_HOME/standalone/deployments/ROOT.war`.
1. Unzip the DXP `.war` file into the `ROOT.war` folder.
1. To trigger deployment of `ROOT.war`, create an empty file named `ROOT.war.dodeploy` in the `$WILDFLY_HOME/standalone/deployments/` folder. On startup, WildFly detects this file and deploys it as a web application.
1. Start the WildFly application server by navigating to `$WILDFLY_HOME/bin` and running `standalone.bat` or `standalone.sh`.

```note::
   After deploying DXP, you may see excessive warnings and log messages, such as the ones below, involving ``PhaseOptimizer``. These are benign and can be ignored. Make sure to adjust your app server's logging level or log filters to avoid excessive benign log messages.
```

```
May 02, 2018 9:12:27 PM com.google.javascript.jscomp.PhaseOptimizer$NamedPass process
WARNING: Skipping pass gatherExternProperties
May 02, 2018 9:12:27 PM com.google.javascript.jscomp.PhaseOptimizer$NamedPass process
WARNING: Skipping pass checkControlFlow
May 02, 2018 9:12:27 PM com.google.javascript.jscomp.PhaseOptimizer$NamedPass process
INFO: pass supports: [ES3 keywords as identifiers, getters, reserved words as properties, setters, string continuation, trailing comma, array pattern rest, arrow function, binary literal, block-scoped function declaration, class, computed property, const declaration, default parameter, destructuring, extended object literal, for-of loop, generator, let declaration, member declaration, new.target, octal literal, RegExp flag 'u', RegExp flag 'y', rest parameter, spread expression, super, template literal, modules, exponent operator (**), async function, trailing comma in param list]
current AST contains: [ES3 keywords as identifiers, getters, reserved words as properties, setters, string continuation, trailing comma, array pattern rest, arrow function, binary literal, block-scoped function declaration, class, computed property, const declaration, default parameter, destructuring, extended object literal, for-of loop, generator, let declaration, member declaration, new.target, octal literal, RegExp flag 'u', RegExp flag 'y', rest parameter, spread expression, super, template literal, exponent operator (**), async function, trailing comma in param list, object literals with spread, object pattern rest
```

## Next Steps

You can [sign in as your administrator user](../../../getting-started/introduction-to-the-admin-account.md) and start [building a solution on DXP](../../../building-solutions-on-dxp/README.md). Or you can explore [additional Liferay DXP setup](../../setting-up-liferay-dxp/setting-up-liferay-dxp.md) topics:

* [Installing the Marketplace Plugin](../../../system-administration/installing-and-managing-apps/getting-started/using-marketplace.md#appendix-installing-the-marketplace-plugin)
* [Accessing EE Plugins During a Trial Period](../../../system-administration/installing-and-managing-apps/installing-apps/accessing-ee-plugins-during-a-trial-period.md)
* [Installing a Search Engine](../../../using-search/installing-and-upgrading-a-search-engine/introduction-to-installing-a-search-engine.md)
* [Securing Liferay DXP](../../securing-liferay/introduction-to-securing-liferay.md)
* [Clustering for High Availability](../../setting-up-liferay-dxp/clustering-for-high-availability/clustering-for-high-availability.md)