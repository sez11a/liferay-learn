# Changing Your Site's Appearance

Changing the site logo, favicon, and theme are some of first look and feel areas you may want to change. Themes are used to set the overall look and feel for your Site. A Site's Pages can be configured to use any theme that has been deployed to your DXP instance.

The DXP Docker image has the _Classic_ theme available by default. In this example, we will deploy a new simple theme and then show you how to switch from one theme to another.

## Changing Your Site's Theme

### Deploy a New Theme

1. Start up the Liferay DXP Docker image:

    ```bash
    docker run -it -p 8080:8080 liferay/portal:7.3.0-ga1
    ```

1. Download the WAR containing the [Acme Sample Theme](./liferay-5b2v-theme.war):

    ```bash
    curl https://learn.liferay.com/dxp/7.x/en/getting-started/changing-your-sites-appearance/liferay-5b2v-theme.war -O
    ```

1. Deploy the WAR containing the theme:

    <!-- ./gradlew deploy -Ddeploy.docker.container.id=$(docker ps -lq) -->

    ```bash
    docker cp liferay-5b2v-theme.war docker-container:path-to-deploy-folder
    ```

This will load the sample theme into your DXP instance. You can check your console for the following message to confirm that the theme successfully deployed:

```
2020-03-11 17:06:35.601 INFO  [fileinstall-/opt/liferay/osgi/war][BundleStartStopLogger:39] STARTED liferay-5b2v-theme_1.0.0 [1112]
```

### Change Your Site's Theme

Configure the Pages for your Site to use the a deployed theme:

1. Open your browser to `https://localhost:8080` and [login as an administrator](./introduction-to-the-admin-account.md).

1. Navigate to the Product Menu → _Site Administration_ → _Site Builder_ → _Pages_.

1. Click the gear icon ![Gear icon](../images/icon-control-menu-gear.png) next to _Public Pages_ to configure them:

    ![Open the Pages screen to configure your Public Pages.](./changing-your-sites-appearance/images/01.png)

1. Scroll down and click the _Change Current Theme_ button:

    ![Click Change Current Theme to select a new theme for your Public Pages.](./changing-your-sites-appearance/images/02.png)

1. Select the deployed sample theme, _Classic with Blue Background._

1. Navigate back to the home page for your Site to confirm that the theme has changed. The background for your Site is now blue.

    ![The home page has a different color background after changing the theme.](./changing-your-sites-appearance/images/03.png)

Your site's theme has been updated.

### Theme Resources

There are many themes available on the [Liferay Marketplace](../advanced-installation-and-upgrades/01-installing-liferay-dxp/10-setting-up-marketplace.md) that can be used to quickly achieve a professional look and feel.

You can also learn how to [create your own theme](../site-building/README.md).

## Changing Your Site's Logo

Coming soon!

## Changing Your Site's Favicon

Coming soon!
