# Updating Liferay Portal CE

Fixes for Liferay Portal CE are delivered as new Portal CE GA releases. For example, CE 7.2 GA2 fixes bugs that exist in CE 7.2 GA1. There is no Patching Tool for Liferay Portal CE. Here are the steps for updating your Portal CE installation to a new GA release:

1. [Back up your DXP system](./backing-up.md) in case you want to revert to it.

1. Shut down the application server.

1. Install the new Portal CE GA release to a new location or a new source control branch to use with your current application server.

1. Migrate your `[Liferay Home]/data` folder, apps, plugins and modules, and any files that you've created or customized (e.g., `portal-ext.properties`, `web.xml`, and OSGi `.config` files) to the new location/branch.

1. Clean up DXP cache.

    Delete the `[Liferay Home]/osgi/state` folder.

    ```bash
    rm -rf [Liferay Home]/osgi/state
    ```

    Empty the `[Liferay Home]/work` folder.

    ```bash
    rm -rf work/*
    ```

1. Delete the old application server cache if you installed the new Portal CE GA on that application server. Please consult the application vendor documentation on where where to find the cache.

    ```note::
       If a module's changes are only internal, the changes are invisible to the OSGi framework, the module stays installed, and the module's state persists. Clearing the OSGi bundle state information before the next DXP startup ensures that such modules reinstall with the appropriate state.
    ```

1. Start the application server for your new Portal CE GA.

1. Run the [`upgrade:check`](../upgrade-stability-and-performance/upgrading-modules-using-gogo-shell.md#checking-upgrade-status) [Gogo Shell](https://help.liferay.com/hc/en-us/articles/360029070351-Using-the-Felix-Gogo-Shell) command to list all modules whose data hasn't been upgraded.

1. Use the [`upgrade:module [module name]`](../upgrade-stability-and-performance/upgrading-modules-using-gogo-shell.md#executing-module-upgrades) Gogo Shell command to upgrade data for these modules.

Congratulations! You've updated your Portal CE installation to the new GA release!