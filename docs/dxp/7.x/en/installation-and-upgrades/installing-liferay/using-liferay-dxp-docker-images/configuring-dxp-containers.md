# Configuring DXP Containers

Everything that is configurable in a DXP installation is configurable in a DXP Docker container.

Here are the most common things to configure:

* [JVM Options](#jvm-options)
* [Portal Properties](#portal-properties)
* [Image-defined Environment Variables](#image-defined-environment-variables)
* [System Properties](#system-properties)
* [System Settings](#system-settings)

```note::
   The configuration use cases that involve providing a file to the container are demonstrated here using `bind mounts <https://docs.docker.com/storage/bind-mounts/>`_. You can also use `volumes <https://docs.docker.com/storage/volumes/>`_ and in some cases, use a ``docker cp`` command. See `Providing File to the Container <./providing-files-to-the-container.md>`_ for more information.
```

## JVM Options

Tomcat's JVM options can be appended or replaced.

### Appending JVM Options to CATALINA_OPTS

JVM options can be appended to Tomcat's `CATALINA_OPTS` variable by specifying them in a `LIFERAY_JVM_OPTS` environment variable when you create the container.

```bash
docker run -it --name [container] -p 8080:8080 -e LIFERAY_JVM_OPTS=[value\ with\ space] liferay/dxp:[tag]
```

```warning::
   In the ``LIFERAY_JVM_OPTS`` value, use backslashes to escape space characters. Don't use quotes.
```

The container runs with the `LIFERAY_JVM_OPTS` value appended to Tomcat's `CATALINA_OPTS`.

### Replacing the setenv.sh File

Another method of configuring JVM options involves overriding Tomcat's `setenv.sh` script. A fast way to create a `setenv.sh` script is to copy one from a DXP container. Here's how to modify a copy of the script and use it in a new container:

1. If you have an existing DXP container, start it. Otherwise, run a new one.

    ```bash
    docker run -it --name tmp-dxp -p 8080:8080 liferay/dxp:[tag]
    ```

1. Copy the `setenv.sh` file from the container.

    ```bash
    docker cp tmp-dxp:/opt/liferay/tomcat/bin/setenv.sh .
    ```

1. [Stop the container](./dxp-docker-container-basics.md#stopping-a-container).

1. Set the JVM options in your copy of `setenv.sh`.

1. Create a host folder and subfolders to create the path `files/tomcat/bin`.

    ```bash
    mkdir -p [host folder]/files/tomcat/bin
    cp setenv.sh [host folder]/files/tomcat/bin
    ```

1. Run a container with a `-v` option that bind mounts to your host folder:

    ```bash
    docker run -it --name [container] -p 8080:8080 -v [host folder path]:/mnt/liferay liferay/dxp:[tag]
    ```

The container uses your `setenv.sh` script's JVM options.

```note::
   Please see `Providing Files to the Container <./providing-files-to-the-container.md#bind-mounting-a-host-folder-to-mnt-liferay>`_ for more information on bind mounting to to the container's ``/mnt/liferay`` folder.
```

```note::
   See `DXP Docker Container Basics <./dxp-docker-container-basics.md>`_ for details on starting and operating DXP containers.
```

## Portal Properties

DXP container [Portal Properties](../../reference/portal-properties.md) can be overridden in these two ways:

* [Using Liferay Env Variables](#using-liferay-env-variables)
* [Using a Portal Properties File](#using-a-portal-properties-file)

### Using Liferay Env Variables

There's an *Env* variable for each [Portal Property](../../reference/portal-properties.md). Env properties override a DXP Docker container's Portal Properties.

1. In the [Portal Properties](https://docs.liferay.com/ce/portal/7.3-latest/propertiesdoc/portal.properties.html) online description, find the property you want to override.

1. Copy the `Env` variable name displayed just below the property description. For example, here's the Env variable for the [`jdbc.default.jndi.name`](https://docs.liferay.com/ce/portal/7.3-latest/propertiesdoc/portal.properties.html#JDBC) Portal Property:

    ```properties
    Env: LIFERAY_JDBC_PERIOD_DEFAULT_PERIOD_JNDI_PERIOD_NAME
    ```

1. Create a container, passing in your new Portal Property value using the `-e` option.

    ```
    docker run -it --name [container] -p 8080:8080 -e LIFERAY_JDBC_PERIOD_DEFAULT_PERIOD_JNDI_PERIOD_NAME=jdbc/MyPool liferay/dxp:[tag]
    ```

    ```warning::
       In the ``Env`` variable value, use backslashes to escape space characters. Don't use quotes.
    ```

    ```note::
       See `DXP Docker Container Basics <./dxp-docker-container-basics.md>`_ for details on starting and operating the containers.
    ```

The properties are visible in the Control Panel at _Configuration_ &rarr; _Server Administration_ &rarr; _Properties_ &rarr; _Portal Properties_.

### Using a Portal Properties File

You can override a DXP container's Portal Properties using a `portal-ext.properties` file. This example uses a [bind mount](./providing-files-to-the-container.md).

1. Create a host folder and a subfolder called `files`.

    ```bash
    mkdir -p [host folder]/files
    ```

1. Add your property overrides to a `portal-ext.properties` file in the `files` subfolder you created. For example,

    ```bash
    echo "jdbc.default.jndi.name=jdbc/MyPool" >> [host folder]/files/portal-ext.properties
    ```

1. Create a container, that includes a bind mount that maps your `portal-ext.properties` file's folder to the container's `/mnt/liferay/files` folder. Since this example's `portal-ext.properties` is in a folder called `files`, you can [bind mount to the container's `/mnt/liferay` folder](./providing-files-to-the-container.md#bind-mounting-a-host-folder-to-mnt-liferay).

    ```bash
    docker run -it --name [container] -p 8080:8080 -v [host folder path]:/mnt/liferay liferay/dxp:[tag]
    ```

The properties are visible in the Control Panel at _Configuration_ &rarr; _Server Administration_ &rarr; _Properties_ &rarr; _Portal Properties_.

## Image-Defined Environment Variables

Liferay images define several environment variables. Some of the variables configure internal things, such as the [Liferay Home](../../reference/liferay-home.md) path and the option to start Tomcat in debug mode. Other variables set [Portal Properties](../../reference/portal-properties.md). Here are the image-defined environment variables that set Portal Properties.

```properties
LIFERAY_MODULE_PERIOD_FRAMEWORK_PERIOD_PROPERTIES_PERIOD_OSGI_PERIOD_CONSOLE=0.0.0.0:11311
LIFERAY_SETUP_PERIOD_WIZARD_PERIOD_ADD_PERIOD_SAMPLE_PERIOD_DATA=false
LIFERAY_SETUP_PERIOD_WIZARD_PERIOD_ENABLED=false
LIFERAY_TERMS_PERIOD_OF_PERIOD_USE_PERIOD_REQUIRED=false
LIFERAY_USERS_PERIOD_REMINDER_PERIOD_QUERIES_PERIOD_ENABLED=false
```

Environment variables that correspond to Portal Properties are prioritized over Portal Properties file settings.

All Docker environment variables, including the ones above, are immutable. If you set any environment variables or rely on the Liferay image-defined environment variables, make sure the have the values you want.

### Environment Variable Options

Here are the options for working with image-defined environment variables:

1. Use the image-defined defaults. They're set automatically.

1. Override a default value by setting the environment variable when running the container. For example, `docker run -e [variable]=[value] ...`.

1. Disable an environment variable by declaring it with no assignment (i.e., no `=` character). Here's the format: `-e [varable]`

    Disabling an image-defined Portal Property environment variable gives you flexibility to specify the value you want in a [Portal Properties file](#using-a-portal-properties-file) on container startup. For example,

    ```bash
    docker run -e [varable] -v [host folder path]:/mnt/liferay ...
    ```

### Example: Working with an Image-Defined Portal Property Environment Variable

The following image-defined Portal Property environment variable declares that users don't have to agree to your terms of use.

```properties
LIFERAY_TERMS_PERIOD_OF_PERIOD_USE_PERIOD_REQUIRED=false
```

Here's how to disable it and work with it using a Portal Properties file:

1. Disable the environment variable and set a bind mount for a Portal Properties file:

    ```bash
    docker run -e LIFERAY_TERMS_PERIOD_OF_PERIOD_USE_PERIOD_REQUIRED -v $(pwd):/mnt/liferay ...
    ```

    The terms of use requirement is based on your Portal Properties. The [default Portal Property setting](https://docs.liferay.com/portal/7.3-latest/propertiesdoc/portal.properties.html) (search for `LIFERAY_TERMS_PERIOD_OF_PERIOD_USE_PERIOD_REQUIRED`) requires the terms of use:

    ```properties
    terms.of.use.required=true
    ```

1. Specify the setting you want in a `portal-ext.properties` file that is in your bind mount path. See [using a Portal Properties file](#using-a-portal-properties-file).

    ```bash
    echo "terms.of.use.required=false" >> ./files/portal-ext.properties
    ```

1. Restart the container.

The container uses your property setting.

## System Properties

[System Properties](https://docs.liferay.com/ce/portal/7.3-latest/propertiesdoc/system.properties.html) can be overridden using a `system-ext.properties` file. This example uses a [bind mount](./providing-files-to-the-container.md).

1. Create a host folder and subfolders to create the path `[host folder]/files/tomcat/webapps/ROOT/WEB-INF/classes`.

    ```bash
    mkdir -p [host folder]/files/tomcat/webapps/ROOT/WEB-INF/classes
    ```

1. Add your property overrides to a `system-ext.properties` file in the `[host folder]/files/tomcat/webapps/ROOT/WEB-INF/classes` folder you created. For example,

    ```bash
    echo "net.sf.ehcache.skipUpdateCheck=false" >> [host folder]/files/tomcat/webapps/ROOT/WEB-INF/classes/system-ext.properties
    ```

1. Run a new container with a `-v` option that bind mounts to your host folder:

    ```bash
    docker run -it --name [container] -p 8080:8080 -v [host folder path]:/mnt/liferay liferay/dxp:[tag]
    ```

    ```note::
       Please see `Providing Files to the Container <./providing-files-to-the-container.md#bind-mounting-a-host-folder-to-mnt-liferay>`_ for more information on bind mounting to to the container's ``/mnt/liferay`` folder.
    ```

The properties are visible in the Control Panel at _Configuration_ &rarr; _Server Administration_ &rarr; _Properties_ &rarr; _System Properties_.

## System Settings

DXP System Settings can be configured in the [Control Panel](../../../system-administration/system-settings/system-settings.md) or by providing [Configuration Files](../../../system-administration/system-settings/using-configuration-files.md) (`.config` files) to the container. You can create a `.config` file from scratch or by exporting the component configuration values from the UI.

Modify the System Settings using one of these ways:

* [Applying Configurations to a New Container](#applying-configurations-to-a-new-container)
* [Applying Configuration Files at Run Time](#applying-configuration-files-at-run-time)
* [Using the Control Panel](../../../system-administration/system-settings/system-settings.md)

### Applying Configurations to a New Container

If you have not yet created a container, follow these steps to provide a `.config` file to a new container using a [bind mount](./providing-files-to-the-container.md):

1. Create a host folder and subfolders to make the path `[host folder]/files/osgi/configs`.

    ```bash
    mkdir -p [host folder]/files/osgi/configs
    ```

1. Copy your `.config` files to the host folder's `files/osgi/configs` subfolder. For example,

    ```
    cp ~/*.config [host folder path]/files/osgi/configs
    ```

1. Run the DXP container with a `-v` option that bind mounts your host folder:

    ```bash
    docker run -it --name [container] -p 8080:8080 -v [host folder path]:/mnt/liferay liferay/dxp:[tag]
    ```

    ```note::
       Please see `Providing Files to the Container <./providing-files-to-the-container.md#bind-mounting-a-host-folder-to-mnt-liferay>`_ for more information on bind mounting to to the container's ``/mnt/liferay`` folder.
    ```

The system component configurations are visible in DXP's Control Panel at _Configuration_ &rarr; _System Settings_, in the screen for that component.

### Applying Configuration Files at Run Time

If you have a container already, you can copy `.config` files to your container at run time using a `docker cp` command like this one:

```bash
docker cp [config file] [container]:/opt/liferay/osgi/configs
```

## Conclusion

Now you know how to configure a DXP container's JVM options, Portal Properties, DXP image Env variable, System Properties, and System Settings.

## Additional Information

* [DXP Docker Container Basics](./dxp-docker-container-basics.md)
* [DXP Container Lifecycle and API](./dxp-container-lifecycle-and-api.md)
* [Providing Files to the Container](./providing-files-to-the-container.md)
* [Installing Apps and Other Artifacts to Containers](./installing-apps-and-other-artifacts-to-containers.md)
* [Patching DXP in Docker](./patching-dxp-in-docker.md)
