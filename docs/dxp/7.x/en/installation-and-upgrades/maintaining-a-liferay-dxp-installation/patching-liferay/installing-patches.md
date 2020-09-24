# Installing Patches

The patching steps for DXP bundles and DXP application server installations are similar. Since DXP bundles include the Patching Tool preconfigured, you can apply patches right away. DXP application server installations, however, require installing and configuring the Patching Tool before patching.

```warning::
   **Always** `back up <../backing-up.md>`_ your database and installation before patching.
```

If you're patching a DXP bundle, continue with the basic patching steps below. If you're patching DXP on an application server, [prepare to patch DXP on an application server](#preparing-to-patch-dxp-on-an-application-server) _before_ following the patching steps.


## Patching Steps

1.  Download the patch to your `patching-tool/patches` folder---don't unzip the patch.

    * Fix Packs and Service Packs are on the [Downloads](https://customer.liferay.com/downloads) page in the Help Center.
    * Hotfixes are in [Help Center](https://help.liferay.com/hc) tickets

1.  Shut down your application server.

    Reasons:

    * On Windows systems, files in use are locked and can't be patched.
    * On Unix-style systems, you can usually replace files that are running but the old ones reside in memory.

1.  Install the patch by running the Patching Tool's `install` command from the `patching-tool` folder:

    ```bash
    cd patching-tool
    ./patching-tool.sh install
    ```

    The output looks like this:

    ```
    Running the install command...
    Installing dxp-1
    Installation complete!
    Install finished!
    Installing hotfix-123
    Install finished!
    ```

1.  Verify that the patch installation by executing the `info` command and checking the information on the currently installed patches:

    ```bash
    ./patching-tool.sh info
    ```

    The output lists the currently installed patches:

    ```
    Loading product and patch information...
    Product information:
      * build number: 7310
      * service pack version:
        - available SP version: 1
        - installable SP version: 1
      * patching-tool version: 3.0.5
      * time: 2020-09-01 20:26Z
      * host: 91WRQ72 (8 cores)

    Currently installed patches:
    ...
    ```

1.  If you installed a Service Pack and its release notes mention micro or minor schema/data changes, use the [Database Upgrade Tool](../../upgrading-liferay/upgrade-basics/using-the-database-upgrade-tool.md) to apply minor changes (required) and any micro changes you want.

    ```important::
       If you're updating from Liferay DXP 7.2 GA1 or Fix Pack 1 to DXP 7.2 SP1 / Fix Pack 2 (or above), you must update the data and database using the Database Upgrade Tool.
    ```

1.  Clean up DXP cache.

    Delete the `[Liferay Home]/osgi/state` folder.

    ```bash
    cd [Liferay Home]
    rm -rf osgi/state
    ```

    Empty the `[Liferay Home]/work` folder.

    ```bash
    rm -rf work/*
    ```

    Delete the application server cache. Please consult the application vendor documentation on where where to find the cache.

    ```warning::
       **Do not delete these two files:** ``patching-backup-deps.zip`` and ``patching-backup.zip``. The Patching Tool creates them in the DXP application's `WEB-INF` folder. The Patching Tool examines them to determine previous Fix Pack files to revert before installing a new Fix Pack.
    ```

    ```note::
       If a module's changes are only internal, the changes are invisible to the OSGi framework, the module stays installed, and the module's state persists. Clearing the OSGi bundle state information before the next DXP startup ensures that such modules reinstall with the appropriate state.
    ```

1.  If you customized DXP's `web.xml` file, merge your customizations into the new `web.xml` file that the Fix Pack includes. Fix Packs always overwrite the existing `web.xml` file.

1.  If the patch has any index updates, configure DXP to update the indexes on startup.

    Use the `info` command to check for index updates.

    ```bash
    cd patching-tool
    ./patching-tool.sh info
    ```

    If there are index updates, set the [`database.indexes.update.on.startup`](https://docs.liferay.com/dxp/portal/7.2-latest/propertiesdoc/portal.properties.html#Database) Portal Property to `true` in a [`portal-ext.properties` file](../../reference/portal-properties.md).

    ```properties
    database.indexes.update.on.startup=true
    ```

    Only indexes that start with `LIFERAY_` OR `IX_` are updated. Make sure that your custom indexes do not use this naming convention.

1.  Start the application server again.

Congratulations! Your DXP instance is patched and running.

```note::
   If the patch doesn't install or if you're unable to resolve errors that occur, please open a `Help Center ticket <https://help.liferay.com/hc/>`_ and provide the full Patching Tool ``info`` output by running ``./patching-tool.sh info > output.txt`` and attaching the ``output.txt`` file to the ticket.
````

## Preparing to Patch DXP on an Application Server

If you installed DXP on an application server, you must first install and configure the Patching Tool before patching DXP.

1.  [Install the Patching Tool](./installing-the-patching-tool.md), if you have not yet installed it.

1.  [Configure the Patching Tool](./configuring-the-patching-tool.md) for your DXP installation by running the `auto-discovery` command.

    ```bash
    cd patching-tool
    ./patching-tool.sh auto-discovery
    ```

1. Continue with the [Patching Steps](#basic-patching-steps) in the previous section.

Now you know how to patch a DXP Bundle and a DXP application server installation.

## Preventing an unstable state

Patching Tool uses multiple json files to determine how to patch your DXP bundle. Before starting the install, Patching Tool checks if the bundle is ready for it.
If the bundle has none (it means no patch is installed) or all (it means there is a patch installed ) of the necessary json files, the install will start, otherwise it will stop.

In case you have accidentally removed these files you can restore them and re-run the install.

If a json file is missing, you can always restore it from the Fix Pack / Hotfix you have installed. Important - the json files for Hotfixes are prefixed with the Hotfix name - e.g. hotfix-123-7310-renames.json.

## Additional Information

* [Installing the Patching Tool](./installing-the-patching-tool.md)
* [Configuring the Patching Tool](./configuring-the-patching-tool.md)
* [Uninstalling Patches](./uninstalling-patches.md)
