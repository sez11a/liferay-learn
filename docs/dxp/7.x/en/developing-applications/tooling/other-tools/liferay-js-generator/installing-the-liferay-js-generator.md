# Installing the Liferay JS Generator

```note::
    To use the Liferay JS Generator, you must have the Liferay JS Portlet Extender activated in your Portal instance. It's activated by default. You can confirm this by opening the Control Menu, navigating to the *App Manager*, and searching for `com.liferay.frontend.js.portlet.extender`.
```

Follow these steps to install the Liferay JS Generator:

1. Install [Node.js](http://nodejs.org/). Note that Node Package Manager (npm) is installed with this as well. You'll use npm to install the remaining dependencies and generator and [configure your npm environment](../../../../site-building/developer-guide/developing-themes/setting-up-your-npm-environment-reference.md).

1. Install [Yeoman](http://yeoman.io/) for the generator:

    ```bash
    npm install -g yo
    ```

1. Install the Liferay JS Generator:

    ```bash
    npm install -g generator-liferay-js
    ```

## Related Information

* [Adapting Existing Apps to Run on Portal](./developer-guide/adapting-apps-for-liferay.md)
* [Migrating React Apps to Liferay DXP](../../../../developing-a-single-page-application/using-angular.md)
* [Migrating Vue JS Apps to Liferay DXP](../../../../developing-a-single-page-application/using-vuejs.md)
* [Migrating Angular Apps to Liferay DXP](../../../../developing-a-single-page-application/using-angular.md)
