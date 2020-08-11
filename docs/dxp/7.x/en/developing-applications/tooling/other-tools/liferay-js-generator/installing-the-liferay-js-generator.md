# Installing the Liferay JS Generator

Follow these steps to install the Liferay JS Generator:

```note::
To use the Liferay JS Generator, you must have the Liferay JS Portlet Extender activated in your Portal instance. It's activated by default. You can confirm this by opening the Control Menu, navigating to the *App Manager*, and searching for ``com.liferay.frontend.js.portlet.extender``.
```

1. Install [Node.js](http://nodejs.org/). Make sure the Node Package Manager (npm) is installed as well. You'll use npm to install the remaining dependencies and generator and [configure your npm environment](../../../../site-building/developer-guide/developing-themes/setting-up-your-npm-environment-reference.md).

1. Install [Yeoman](http://yeoman.io/) for the generator:

    ```bash
    npm install -g yo
    ```

1. Install the Liferay JS Generator:

    ```bash
    npm install -g generator-liferay-js
    ```

## Generator and Sub-generator Commands

The JS Generator has several sub-generators:

| command | description | Generator Version |
| --- | --- | --- |
| `yo liferay-js` | Generates a JavaScript-based application | All versions |
| `yo liferay-js:adapt` | Adapts an existing application | 9.5.0+ |

## Related Information

* [Adapting Existing Apps to Run on Portal](./developer-guide/adapting-apps-for-liferay.md)
* [Migrating React Apps to Liferay DXP](../../../../developing-a-single-page-application/using-angular.md)
* [Migrating Vue JS Apps to Liferay DXP](../../../../developing-a-single-page-application/using-vuejs.md)
* [Migrating Angular Apps to Liferay DXP](../../../../developing-a-single-page-application/using-angular.md)
