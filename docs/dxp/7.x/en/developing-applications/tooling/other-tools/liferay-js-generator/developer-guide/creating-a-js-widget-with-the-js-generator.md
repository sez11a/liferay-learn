# Creating a JavaScript App with the Liferay JS Generator

You can create pure JavaScript applications, including the major frameworks (React, Angular, and VueJS), with the Liferay JS Generator. Creating your JavaScript application with the Liferay JS Generator, as opposed to just migrating your application ([React](../../../../developing-a-single-page-application/using-react.md)|[Angular](../../../../developing-a-single-page-application/using-angular.md)|[VueJS](../../../../developing-a-single-page-application/using-vuejs.md)) to use the Liferay JS Toolkit, lets you modify your application further, taking advantage of Portal features such as system and instance setting configuration and localization. You can create a JavaScript application with the Liferay JS Generator to run on Portal in just a couple steps:

1. Run the Liferay JS Generator.
1. Build the JAR and copy it to the Docker container.

This example uses a Docker image with a fresh install of Liferay DXP.

> This example runs on Liferay DXP 7.3+

## Run the Liferay JS Generator

1. Install the [Liferay JS Generator](../installing-the-js-generator-and-creating-js-portlets.md).
1. Open the command line and run the Liferay JS Generator:

    ```bash
    yo liferay-js
    ```

1. Answer the prompts to generate the JavaScript widget. The sample answers below create a React widget with all features and sample code. Note that the answer is No (n) to the "Do you have a local installation of Liferay for development?" question so the JAR file can be deployed manually to a Docker container.

    ```bash
    ? What type of project do you want to create? React Widget
    ? What name shall I give to the folder hosting your project? my-react-widget
    ? What is the human readable description of your project? My React Widget
    ? Do you want to add localization support? Yes
    ? Do you want to add configuration support? Yes
    ? Under which category should your widget be listed? category.sample
    ? Do you have a local installation of Liferay for development? No
    ? Do you want to generate sample code? Yes
       create package.json
       create README.md
       create .gitignore
       create .npmbuildrc
       create .npmbundlerrc
       create .npmignore
       create assets\.placeholder
       create features\localization\Language.properties
       create features\configuration.json
       create assets\css\styles.css
       create .babelrc
       create src\index.js
       create src\AppComponent.js
    ```

    ```note::
      To use configuration support in your widget, you must have Liferay DXP/Portal CE 7.1 with JS Portlet Extender 1.1.0 or Liferay DXP/Portal CE 7.2+.
    ```

    ```tip::
      If you specify your app server information when your widget is generated, you can deploy your widget by running the ``npm run deploy`` command. The ``liferayDir`` entry in the widget's `.npmbuildrc` defines the app server.
    ```

The generator automatically adds a few npm scripts to the project's `package.json` so you can build and deploy your project to your server. See [Liferay JS Generator Commands Reference](../reference/liferay-js-generator-commands-reference.md) for the available commands.

```tip::
  By default, the webpack server uses port 8080, which conflicts with the port used by Tomcat. You can point the webpack server to a different port by setting the ``port`` key in ``.npmbuildrc``: ``"webpack": {"port": 2070}``.
```

## Build the Jar and Copy it to the Docker Container

You can download and unzip the [example generated app](https://github.com/liferay/liferay-learn/tree/master/docs/dxp/7.x/en/developing-applications/tooling/other-tools/liferay-js-generator/creating-a-js-widget-with-the-js-generator/liferay-g2a8.zip) if you want to deploy it or compare your code at this point:

    ```bash
    curl https://github.com/liferay/liferay-learn/tree/master/docs/dxp/7.x/en/developing-applications/tooling/other-tools/liferay-js-generator/developer-guide/creating-a-js-widget-with-the-js-generator/liferay-g2a8.zip
    
    unzip liferay-g2a8.zip
    cd my-react-widget
    npm install
    ```

1. Start the Docker container:

    ```bash
    docker run -it -p 8080:8080 liferay/portal:7.3.0-ga1
    ```

1. Build the generated application's JAR file:

    ```bash
    npm run build
    ```

1. Copy the generated application's JAR to the Docker container:
  
    ```bash
    cd dist
    cp my-react-widget-1.0.0.jar docker-container-name:/path/to/deploy/folder
    ```
  
1. Confirm the deployment to the Liferay Docker container console. The log message should appear in the Docker console. The example produces the log message below:

    ```bash
    INFO [fileinstall-/opt/liferay/osgi/modules][BundleStartStopLogger:39] STARTED my-react-widget_1.0.0 [1132]
    ```
  
1. Verify that the app is available. Open your browser to `https://localhost:8080`. Click the (![Add button](../../../../../images/icon-add-app.png)) in the Control Menu and drag the app onto the page. The example is listed as *My React Widget* under the *Sample* category.
  
    ![The generated sample React widget displays basic information about the app.](./creating-a-js-widget-with-the-js-generator/images/01.png)

Great! You successfully built and deployed a generated app.

## Related Information

* [Migrating React Apps to Liferay DXP](../../../../developing-a-single-page-application/using-react.md)
* [Migrating Vue JS Apps to Liferay DXP](../../../../developing-a-single-page-application/using-vuejs.md)
* [Migrating Angular Apps to Liferay DXP](../../../../developing-a-single-page-application/using-angular.md)