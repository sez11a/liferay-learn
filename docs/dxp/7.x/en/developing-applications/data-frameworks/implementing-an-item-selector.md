# Implementing an Item Selector

Item selectors are pop-up dialogs for selecting assets, such as documents, videos, Users, Roles, and Sites.

By configuring the item selector's criteria and defining its usage, you have full flexibility in how to use the item selector dialog for your own application.

![Item selectors pop up so users can select assets.](./implementing-an-item-selector/images/01.png)

Here you'll learn how to create an Item Selector. 

## Start with a Sample Module

To implement an item selector, you must embed it in an application, such as a module for a widget. This example uses an [MVC Portlet](../developing-a-java-web-application/using-mvc/creating-an-application-with-mvcportlet.md) with a JSP view. The item selector displays a list of Roles to be selected. 

To begin, download the sample module:

```bash
curl https://learn.liferay.com/dxp/7.x/en/developing-applications/data-frameworks/liferay-f5d5.zip -O
```

```bash
unzip liferay-f5d5.zip
```

Now you can deploy the sample module: 

1. Start a Liferay DXP Docker container with the following command:

    ```bash
    docker run -it -p 8080:8080 --name lrdev liferay/portal:7.3.2-ga3
    ```

1. Run the following commands from the root of the module to build and deploy to your Docker container:

    ```bash
    ./gradlew deploy -Ddeploy.docker.container.id=$(docker ps -lq)
    ```

    ```tip::
       This command is the same as copying the deployed jars to ``/opt/liferay/osgi/modules`` on the Docker container.
    ```

1. Confirm the deployment in the Liferay Docker container console.

    ```bash
    STARTED com.acme.f5d5.web_1.0.0 [1017]
    ```

The example portlet module is deployed. When you add it to a page, you'll see it's a simple portlet with one button: 

![The portlet has one button that opens the Item Selector.](./images/02.png)

Click the _Select_ button and the Item Selector appears: 

![The Item Selector shows items that can be selected by checking the box.](./images/03.png)

Select an item and that item's value appears in a JavaScript alert box. Since this Item Selector selects Roles, the value is the primary key of the Role selected. 

Now you can see how this works. 

## Setting an Item Selector's Criteria in your Controller

Open the `F5D5MVCPortlet.java` class. In an MVC Portlet, the portlet class is the controller class (the C in MVC). It must do two things: 

- Define the necessary criteria for the selector (i.e., what entity does it select?)
- Create a URL for that criteria

1. At the bottom of the class, OSGi injects an [`ItemSelector` class](https://github.com/liferay/liferay-portal/blob/7.3.4-ga5/modules/apps/item-selector/item-selector-api/src/main/java/com/liferay/item/selector/ItemSelector.java) instance because of the `@Reference` annotation. 

   ```java
   @Reference
   private ItemSelector _itemSelector;
   ```

1. Scroll to the portlet's `render` method. 

1. A criterion class to represent the desired entities to display in the item selector is created. Criterion classes must implement the [`ItemSelectorCriterion` interface](http://docs.liferay.com/portal/7.3-latest/apps/item-selector-3.0.4/javadocs/com/liferay/item/selector/ItemSelectorCriterion.html).

   This example uses a reference to [`RoleItemSelectorCriterion`](http://docs.liferay.com/portal/7.3-latest/apps/roles-3.0.4/javadocs/com/liferay/roles/item/selector/RoleItemSelectorCriterion.html) so that Roles are shown in the selector. It's defined by creating a new instance of the class:

   ```java
   RoleItemSelectorCriterion itemSelectorCriterion =
        new RoleItemSelectorCriterion();
   ```

   ```tip::
      If no criterion exists for the type of entity that you need, you can define your own ``ItemSelectorCriterion`` by extending `BaseItemSelectorCriterion <https://github.com/liferay/liferay-portal/blob/7.3.4-ga5/modules/apps/item-selector/item-selector-api/src/main/java/com/liferay/item/selector/BaseItemSelectorCriterion.java>`__.
   ```

1. Next, you need a return type class to represent the information the entities provided when users select them. Return type classes must implement the [`ItemSelectorReturnType` interface](http://docs.liferay.com/portal/7.3-latest/apps/item-selector-3.0.4/javadocs/com/liferay/item/selector/ItemSelectorReturnType.html) interface. For example, a return type class may be used to return the entity's URL, UUID, or primary key. The return type class is added to the criterion class created previously.

   ```important::
      Every criterion used **must** have at least one return type associated with it.
   ```

   This example uses a reference to [`UUIDItemSelectorReturnType`](http://docs.liferay.com/portal/7.3-latest/apps/item-selector-3.0.4/javadocs/com/liferay/item/selector/criteria/UUIDItemSelectorReturnType.html) to define the selected Roles' `UUID` value as the crucial data to return. If multiple Roles are selected, they are returned as a comma-delimited list.

   Define the return type by adding an instance of the class to a list and then registering it with the item criterion:

   ```java
    ItemSelectorReturnType[] returnTypeArray =
        new ItemSelectorReturnType[1];

    returnTypeArray[0] = new UUIDItemSelectorReturnType();

    List<ItemSelectorReturnType> itemSelectorReturnTypes =
        ListUtil.fromArray(returnTypeArray);

    itemSelectorReturnTypes.add(new UUIDItemSelectorReturnType());

    itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
        itemSelectorReturnTypes);
   ```

   ```tip::
       If no return type exists for the type of information that you need, then you can define your own `ItemSelectorReturnType <https://github.com/liferay/liferay-portal/blob/7.3.4-ga5/modules/apps/item-selector/item-selector-api/src/main/java/com/liferay/item/selector/ItemSelectorReturnType.java>`__ implementation.
   ```

The item selector uses these two classes to decide what selection views of items (presented as tabs) to show and how to identify each item.

1. Now you can use the criteria to generate a URL for the item selector. This URL creates the item selector dialog in your front-end code.

   The [`RequestBackedPortletURLFactory` class](http://docs.liferay.com/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/portlet/RequestBackedPortletURLFactory.html) can quickly generate an item selector URL using the criteria:

   ```java
   RequestBackedPortletURLFactory requestBackedPortletURLFactory =
       RequestBackedPortletURLFactoryUtil.create(renderRequest);

   PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
       requestBackedPortletURLFactory, "selectItem",
       itemSelectorCriterion);
   ```

```important::
   The String you use to generate the URL (in this example, ``selectItem``) is the dialog's event name. This must match a value you'll use later when creating the dialog in your front-end code.
```

1. Add the item selector URL to the `renderRequest` so that it's available in the JSP:

   ```java
   renderRequest.setAttribute("itemSelectorURL", itemSelectorURL);
   ```

   The `view.jsp` file is where the front-end code is defined. The `renderRequest` object in your Java class's `render` method is passed to the JSP file later.

1. Finally, call `MVCPortlet`'s `render` method to continue the rendering process once your code is executed:

   ```java
   super.render(renderRequest, renderResponse);
   ```

That's the controller code. Execution now passes to the view layer (the V in MVC), which is implemented in the `view.jsp` file. 

## Use the Item Selector in Your View

You must retrieve the item selector and define a way to use it in your front-end code Open `view.jsp` from the example. 

1. You begin by retrieving the item selector URL from the request where the controller stored it:

   ```jsp
   <%
   String itemSelectorURL = String.valueOf(request.getAttribute("itemSelectorURL"));
   %>
   ```

   Once you have the URL, you must provide a way to open the item selector and then define how to use the result.

1. A button is a simple UI element you can use to open an item selector. You can use a [Clay button](https://clayui.com/docs/components/button.html) tag to create a button to open your item selector: 

   ```jsp
   <clay:button
     id='<%= liferayPortletResponse.getNamespace() + "selectRole" %>'
     label="Select"
   />
   ```

   The `clay:button` tag creates a button (with the ID `selectRole` and the label *Select* displayed on the screen) on your widget. This button can be identified by the String `<portlet:namespace />selectRole`.

1. Use the `<script>` tag to embed JavaScript that opens the item selector: 

   ```jsp
   <script>
      var selectRoleButton = document.getElementById('<portlet:namespace />selectRole');

      selectRoleButton.addEventListener(
         'click',
         function(event) {
            var itemSelectorDialog = new Liferay.Util.openSelectionModal(
               {
                       onSelect: function (selectedItem) {
                           if (selectedItem) {
                               // Use the selected item value(s) here.
                               alert (selectedItem.value);
                           }
                       },
                  selectEventName: 'selectItem',
                  title: 'Select Role',
                  url: '<%= itemSelectorURL %>'
               }
            );
         }
      );
   </script>
   ```

This snippet of JavaScript first retrieves the "Select Role" button through its identifier (`portlet:namespace />selectRole`). Then it adds an event listener to create the item selector dialog when clicked.

The `Liferay.Util.openSelectionModal` method creates the dialog.

The `onSelect` field must define a function to handle the value when it is clicked. Define the dialog's behavior when the user makes a selection within this function. This implementation shows an alert box containing the selected value. 

The value for the `eventName` field must match the String you used with the `RequestBackedPortletURLFactory` in the Java code (in this example, `selectItem`). You must also use the URL that you retrieved from the `request` object previously for the `url` field when defining the item selector dialog.

```tip::
   If you want your item selector to support selecting multiple items, then you can enable multiple selection by adding ``multiple: true`` to the ``openSelectionModal`` call.
```

Use the item selection, stored previously as `selectedItem`. The data type and information contained in the result `selectedItem` depends on what return type class you used in the Java code. Since this example uses `UUIDItemSelectorReturnType`, the data is a String value with the UUIDs of one or more selected items.

Inside the selection function, you implement how you want to use the value. This example shows a simple JavaScript alert. 

Now that you've seen how it works, you can replace the JavaScript alert with something more useful. 

## Add A Form

When you use an item selector, you want the selected value inserted into a form. Here's how to do that: 

1. Open `view.jsp`. 

1. Find the `<clay:button>` tag. Surround it with a form like this: 


```jsp
<form name="<portlet:namespace/>fm">
	<input name="role" /> 
	<clay:button
		id='<%= liferayPortletResponse.getNamespace() + "selectRole" %>'
		label="Select"
	/>
</form>
```
   Now you have a form with one input field called `role`. 

1. Scroll down to the comment that reads `Use the selected item value(s) here`. Replace the JavaScript alert with a call to Liferay's front-end `setFormValues` method: 

   ```jsp
   Liferay.Util.setFormValues(document.<portlet:namespace />fm, {
          role: selectedItem.value
   });
   ```

1. Redeploy the portlet:

   ```bash
   ./gradlew deploy -Ddeploy.docker.container.id=$(docker ps -lq)
   ```

1. Now select an item as you did before. Its ID is inserted into the form field you created. 

## Conclusion

Congratulations! You now know the basics for implementing an item selector!
