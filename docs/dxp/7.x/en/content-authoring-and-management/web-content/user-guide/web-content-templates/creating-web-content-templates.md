# Creating Web Content Templates

Structures define the available fields for creating Web Content, while Web Content Templates define how the [Structure's](../web-content-structures/creating-structures.md) content is presented on the page. A Web Content Template can be associated with a Structure or even [embedded in another Web Content Template](./advanced-web-content-templates/embedding-web-content-templates.md). Changes in the Structure may require changes in the associated Web Content Template, if a Structure field name referenced in the Template is renamed or removed.

```note::
   Web Content Templates are written in the FreeMarker Template Language (FTL).
```

Download the [Newspaper Article Structure and Template](/dxp/7.x/en/content-authoring-and-management/web-content/user-guide/web-content-templates/creating-web-content-templates/liferay-a2e7.zip) to follow along with the example. 

To create a Web Content Template, follow these steps:

1. Open the Product Menu and go to the Site Menu &rarr; *Content & Data* &rarr; *Web Content*.
1. Select the *Templates* tab.
1. Click the *Add button* (![Add Template](../../../../images/icon-add.png)) to create a new Web Content Template.
1. Enter the title of your new Web Content Template and add your code to the script window. You can add the code from the example *Newspaper Article* Template by clicking the *Choose File* button below the Script window and importing the `/liferay-a2e7/a2e7-impl/newspaper-article-template.ftl` file. If you're creating a Web Content Template to [embed in another Template](./advanced-web-content-templates/embedding-web-content-templates.md), you can skip to step eight.

    ![Add your template code (FreeMarker) to the script window.](./creating-web-content-templates/images/01.png)

1. Optionally link a Structure to the Template by clicking *Select* under the *Structure* field in the *Properties* panel on the right and clicking the name of the Structure in the dialog that appears. See [Creating Structures](../web-content-structures/creating-structures.md) for more information on creating Structures.

    To follow along with the example, [Create a new Structure](../web-content-structures/creating-structures.md) called *Newspaper Article Structure* and replace the contents of the *Source* tab with the `/liferay-a2e7/a2e7-impl/newspaper-article-structure.ftl` file.
 
    ![You can link the Template to a Structure through the Properties panel.](./creating-web-content-templates/images/02.png)

    The fields configured in the Structure appear in the *Fields* panel on the left side of the script window.

    ![Available Structure field variables are added to the Fields panel on the left side of the Script window.](./creating-web-content-templates/images/03.png)

1. Place your cursor in the script editor where you want to add the field to the Template and click the field in the *Fields* panel to add it. An example is shown below:

    ```markup
    ${Title.getData()}
    ${Content.getData()}
    <#if Imagem1j5.getData()?? && Imagem1j5.getData() != "">
    	<img alt="${Imagem1j5.getAttribute("alt")}" data-fileentryid="${Imagem1j5.getAttribute("fileEntryId")}" src="${Imagem1j5.getData()}" />
    </#if>
    ```

1. Add any additional HTML or FreeMarker that your require for the Template. The example below wraps the fields with some basic HTML elements to provide formatting: 

    ```markup
    <h1>${Title.getData()}</h1>
    <hr>
    <p>${Content.getData()}</p>
    <#if Imagem1j5.getData()?? && Imagem1j5.getData() != "">
    	<img 
        alt="${Imagem1j5.getAttribute("alt")}" 
        class="text-center" 
        data-fileentryid="${Imagem1j5.getAttribute("fileEntryId")}" 
        src="${Imagem1j5.getData()}" 
      />
    </#if>
    ```

1. Click *Save*.

Once you've created the Web Content Template, you can [create a Web Content Article](../web-content-articles/adding-a-basic-web-content-article.md) to use it.

## Related Information

* [Creating Structures](./creating-structures.md)
* [Adding a Basic Web Content Article](../web-content-articles/adding-a-basic-web-content-article.md)
