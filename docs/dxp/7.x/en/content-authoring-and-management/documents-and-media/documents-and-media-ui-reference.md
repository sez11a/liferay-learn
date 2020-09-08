# Documents and Media UI Reference

To get to the _Documents and Media_ menu, navigate to the _Site Administration_ &rarr; _Content & Data_ &rarr; _Documents and Media_.

![The Documents and Media menu is found in Site Administration.](./documents-and-media-ui-reference/images/01.png)

There are three tabs: _Documents and Media_, _Document Types_, and _Metadata Sets_.

## Documents and Media Tab

The _Documents and Media_ Tab displays that site's folders and documents, beginning at the Root folder. Here, users can upload a single document or multiple documents, add a new folder, add a new Repository (for example, Sharepoint), or a new shortcut.

Click the (![Add icon](../../images/icon-add.png)) to begin using the _Documents and Media_ application.

![Click the add icon then a selection to begin populating the Documents and Media app.](./documents-and-media-ui-reference/images/04.png)

## Document Types Tab

_Document Types_ are made of metadata fields that users fill out to and help define the purpose of Document Library files. They provide additional, searchable information about uploaded documents. 

![The Document Types Tab contains all Document Types which contain metadata sets.](./documents-and-media-ui-reference/images/02.png)

Click the (![Add icon](../../images/icon-add.png)) to create a new Document Type.

To learn more about using Document Types, see [Defining Document Types](./uploading-and-managing/managing-metadata/defining-document-types.md).

## Metadata Set Tab

Metadata Sets are groups of fields that can be added to Document Types. 

Metadata Sets can be grouped in a hierarchy to be used as the foundation of another metadata set (an "extension"). A "child" metadata set appears on the same level as the parent.

![The Metadata Set Tab contains all Metadata Sets.](./documents-and-media-ui-reference/images/03.png)

Click the (![Add icon](../../images/icon-add.png)) to create a new Metadata Set.

To learn more about Metadata Sets, see [Using Metadata Sets](./uploading-and-managing/managing-metadata/using-metadata-sets.md).

## Documents and Media Settings

You can access the application's settings from any of the three tabs by clicking the (![Options icon](../../images/icon-options.png)) to access the _Options_ menu.

![The Options menu contains the application settings.](./documents-and-media-ui-reference/images/05.png)

### Access from Desktop

You can access the Documents and Media repository from your desktop using WebDAV. To do so, click _Access from Desktop_ to access the WebDAV URL.

![The WebDAV URL provides desktop access to the Documents and Media repository.](./documents-and-media-ui-reference/images/06.png)

### Edit

Users can enable or disable [Workflow](../../process-automation/workflow/user-guide/activating-workflow.md) for all Document Types. To learn more about Workflows in general, see [Introduction to Workflow](../../process-automation/workflow/user-guide/introduction-to-workflow.md).

![Select a Workflow definition to apply to Document Types.](./documents-and-media-ui-reference/images/07.png)

Workflows for Documents and Media are managed here.

### Home Folder Permissions

Users can manage the permissions for the _Documents and Media_ Home folder.

See [Understanding Roles and Permissions](../../users-and-permissions/roles-and-permissions/understanding-roles-and-permissions.md) to learn more.

### Export/ Import

You can [Export or Import](../../site-building/building-sites/importing-exporting-pages-and-content.md) Documents and Media `LAR` (Liferay Archive) files. There are two primary places Export/Import is used: Sites and apps. Here, you can export the contents of your DM application or import documents from another one.

![You can import or export documents to and from Liferay ARchive (LAR) files.](./documents-and-media-ui-reference/images/08.png)

### Configuration

You can configure DM email notification settings on the _Configuration_ menu. See [Connecting to a Mail Server](../../installation-and-upgrades/setting-up-liferay-dxp-configuring-mail/connecting-to-a-mail-server.md) to set up a mail server first.

Email templates contain _Definition of Terms_ which are placeholders that parse information such as the system administrator, the recipient, and the name of the document.

#### Email From Tab

Users can enter a name and email address to populate the email's `from` field.

![Enter a name and email address to populate the from field. This can be important if recipients use rules to filter their mail.](./documents-and-media-ui-reference/images/09.png)

#### Document Added Email

Recipients receive this notification if a new document has been added.

![Place the appropriate information in to the template for "document added" notifications.](./documents-and-media-ui-reference/images/10.png)

#### Document Updated Email

Recipients receive this notification if an existing document has been updated.

## Additional Information

* [Introduction to Documents and Media](./introduction-to-documents-and-media.md)
* [Importing/Exporting Sites and Contents](../../site-building/building-sites/importing-exporting-pages-and-content.md)
