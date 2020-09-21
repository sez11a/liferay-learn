# Enabling Ratings in Your Application

The [ratings system](../user-guide/using-the-ratings-system.md) in Liferay DXP shows buttons to rate different types of content in various ways, such as with stars or thumbs (up or down). You can enable ratings for any assets using the [asset framework]().

![The ratings system shows the ratings and ratings buttons for any enabled content.](./enabling-ratings-in-your-application/images/01.png)

Here, you will walk through the process of enabling ratings for content by using the ratings taglib.

## Start with a Sample Module

In order to implement ratings in your application, you must first retrieve an entity to add the ratings to. The example here uses an [MVC Portlet](../../../developing-applications/developing-a-java-web-application/using-mvc/creating-an-application-with-mvcportlet.md) with a JSP view as a starting point.

To begin adding ratings, download the sample module:

```bash
curl https://learn.liferay.com/dxp/7.x/en/collaboration-and-social/social-tools/02-developer-guide/enabling-ratings-in-your-application/liferay-r9z4.zip -O
```

```
unzip liferay-r9z4.zip
```

Next, you'll enable ratings for this application.

## Enable Ratings 

To enable ratings for your application, you must define an OSGi component with ratings enabled, and then display the ratings using the taglib in your view.

### Configure Your OSGi Component

Define an OSGi component that implements the [`PortletRatingsDefinition`](https://github.com/liferay/liferay-portal/blob/7.3.4-ga5/portal-kernel/src/com/liferay/ratings/kernel/definition/PortletRatingsDefinition.java) interface. See the example [`R9Z4JSPPortlet.java`](./enabling-ratings-in-your-application/liferay-r9z4.zip/r9z4-web/src/main/java/com/acme/r9z4/web/internal/r9z4/R9Z4JSPPortlet.java) file for the full context of this class.

```java
@Component(
    immediate = true,
    property = {
        "com.liferay.portlet.display-category=category.sample",
        "com.liferay.portlet.instanceable=true",
        "javax.portlet.display-name=R9Z4",
        "javax.portlet.init-param.template-path=/",
        "javax.portlet.init-param.view-template=/view.jsp",
        "javax.portlet.security-role-ref=power-user,user"
    },
    service = Portlet.class
)
public class R9Z4JSPPortlet extends MVCPortlet implements PortletRatingsDefinition {
```

This class both extends [`MVCPortlet`](https://github.com/liferay/liferay-portal/blob/7.3.4-ga5/portal-kernel/src/com/liferay/portal/kernel/portlet/bridges/mvc/MVCPortlet.java) and implements `PortletRatingsDefinition`, since it represents an MVC Portlet implementation that has ratings enabled for it.

Next, you must override the `getDefaultRatingsType` method and return one of the available [ratings types](../user-guide/using-the-ratings-system.md#ratings-types) to define the default ratings type for your application. If your application uses a custom asset, then this becomes the default ratings type foro that asset. Use the [`RatingsType`](https://github.com/liferay/liferay-portal/blob/7.3.4-ga5/portal-kernel/src/com/liferay/ratings/kernel/RatingsType.java) enumeration class to return your chosen type, like stars:

```java
@Override
public RatingsType getDefaultRatingsType() {
    return RatingsType.STARS;
}
```

```note::
   The default ratings type for the asset type can still be overridden per Site via the `Site Settings` screen, or for across the entire DXP instance from _Control Panel_ → _Instance Settings_ → _Social_.
```

Finally, override the `getPortletId` method to return the ID of the portlet that uses the entity you are applying ratings to.

```java
@Override
public String getPortletId() {
    return "R9Z4";
}
```

```tip::
   If you want to return an ID for an existing portlet within DXP, then you can use the `PortletKeys <https://github.com/liferay/liferay-portal/blob/7.3.4-ga5/portal-kernel/src/com/liferay/portal/kernel/util/PortletKeys.java>`__ class to choose the ID for that portlet.
```

### Use the Ratings Tag to Display Ratings

Next, retrieve the asset to display ratings for, such as a web content article in this example.

Add the following code to the sample module's `view.jsp`:

```jsp
<%
List<JournalArticle> journalArticles = JournalArticleLocalServiceUtil.getArticles(themeDisplay.getScopeGroupId());

JournalArticle firstArticle = journalArticles.get(0);
%>
```

```note::
   In this example, the `view.jsp <./enabling-ratings-in-your-application/liferay-r9z4.zip/r9z4-web/src/main/resources/META-INF/resources/view.jsp>`__ file includes all of the logic to retrieve the asset. However, for an MVC Portlet implementation in a production environment, you should instead separate this code into the ``render`` logic within your Java code. See the `MVC Portlet Guide <../../../developing-applications/developing-a-java-web-application/using-mvc/creating-an-application-with-mvcportlet.md>`__ for more information on creating MVC Portlets.
```

Then, once you have the asset, use the `liferay-ratings` tag to display ratings for the asset. The `className` and `classPK` attributes are required for the tag.

```jsp
<liferay-ratings:ratings
    className="<%= JournalArticle.class.getName() %>"
    classPK="<%= Long.valueOf(firstArticle.getArticleId()) %>"
    type="stars"
/>
```

```note::
   You must declare the ``liferay-ratings`` taglib to use it in your view.
```

```tip::
   In this example, the ratings are shown beneath the chosen asset's title. The ``liferay-ratings`` tag should normally be displayed near where the asset's information is shown.
```

Now ratings will display on pages with your view.

## Test Your Application

Now you're ready to test your module. Use the following steps to test with the provided sample module:

1. First, start a Liferay DXP Docker image to test with:

    ```bash
    docker run -it -p 8080:8080 liferay/portal:7.3.4-ga5
    ```

1. Build and deploy the sample module:

    ```bash
    ./gradlew deploy -Ddeploy.docker.container.id=$(docker ps -lq)
    ```

    ```note::
       This command is the same as copying the deployed jars to ``/opt/liferay/osgi/modules`` on the Docker container.
    ```

1. Confirm the deployment in the Docker console.

    ```
    STARTED com.acme.r9z4.web_1.0.0
    ```

### Create Content for Testing

This example retrieves web content on your Site to display ratings for. If no web content exists yet on your Site, then create an article to test ratings with:

1. Log into DXP, and then navigate to _Content & Data_ &rarr; _Web Content_ in the product menu.

    ![Navigate to Web Content to create a new article for testing.](./enabling-ratings-in-your-application/images/02.png)

1. Click the Add (![Add icon](../../../images/icon-add.png)) icon, and then click _Basic Web Content_ to begin creating a new article.

    ![Use the Basic Web Content structure to add a new article for testing.](./enabling-ratings-in-your-application/images/03.png)

1. Add a title and click _Publish_ to finish publication.

    ![Fill in a title and publish the content.](./enabling-ratings-in-your-application/images/04.png)

### Deploy the Widget and Verify

Finally, deploy the widget from your module and verify that ratings display with the content title:

1. On the home page of your Site, click the Edit (![Edit icon](../../../images/icon-edit.png)) icon to edit the page.

    ![Click the Edit icon to edit the page and add your widget.](./enabling-ratings-in-your-application/images/05.png)

1. Click the Cards view (![Cards icon](../../../images/icon-view-type-cards.png)) icon, and then click _Widgets_, to open the menu to add a new widget.

    ![Click Fragments and Widgets, and then Widgets.](./enabling-ratings-in-your-application/images/06.png)

1. Locate the _R9Z4_ widget in the list of widgets, and place it on the page.

    ![Place the sample widget on the page.](./enabling-ratings-in-your-application/images/07.png)

1. Click _Publish_ to finalize.

1. Verify that the ratings appear beneath the title of your web content.

    ![The chosen ratings type appears in the sample widget for the chosen content.](./enabling-ratings-in-your-application/images/08.png)

The widget displays a web content article's title, and the ratings appear beneath it.

## Conclusion

Congratulations! You now know how to use the `liferay-ratings` tag in your views, and have added enabled ratings for an application in Liferay DXP.

## Additional Information

* [Using the Ratings System](../user-guide/using-the-ratings-system.md)