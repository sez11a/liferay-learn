# Managing Workflows

The _Workflow_ application allows users to manage their workflow processes; they can upload a new workflow definition, and make changes to the definitions. Lastly, they can delete workflows.

## Workflow Definition Publication Permissions

Users with permission to edit or publish workflow definitions can add [Groovy scripts](../../../system-administration/using-the-script-engine/using-the-script-engine.md) to the workflow. Access to the scripting engine means access to the Java Virtual Machine (JVM) of the server. Users who publish (or edit) workflow definitions containing scripts, therefore, can get access to any data within the reach of the JVM, such as data contained in a separate [Virtual Instance](../../../system-administration/virtual-instances.md) of Liferay DXP itself. 

Because of this far-reaching access, permission to create or edit workflow definitions is limited to Regular Administrators of the Default Virtual Instance. For Liferay DXP customers, Site Administrators who can access the Kaleo Designer and Kaleo Forms Admin applications can also edit and publish workflows.

To grant Users with these Roles the workflow publication access in additional Virtual Instances,

1. Make sure you understand the access you're granting these administrators.
1. Navigate to Control Panel &rarr; System Settings &rarr; Workflow &rarr; Workflow Definition. 
1. Check the box for the setting _Allow Administrators to Publish and Edit Workflows_.

This only applies to Virtual Instances that have been added to the system. The Default Virtual Instance provides out of the box workflow publication access to Regular Administrators, via Control Panel &rarr; Workflow &rarr; Process Builder and for Liferay DXP subscribers via the Kaleo Forms Admin application.

## Uploading a New Workflow Definition

Workflow process definitions can be [built in Liferay](./building-workflows.md) or uploaded in `XML` format. DXP workflow definitions are written in `XML` format; see the [Introduction to Crafting XML Workflow Definitions](https://help.liferay.com/hc/articles/360029147791-Introduction-to-Crafting-XML-Workflow-Definitions) to learn more.

To upload a new definition:

1. Navigate to the _Control Panel_ &rarr; _Workflow_ &rarr; _Process Builder_.
1. Click the _New Workflow_ button (![Add](../../../images/icon-add.png)).
1. If you have a local XML definition file based on an embedded workflow, you can upload it to DXP. Click the _Source_ tab.
1. Enter a name for the new definition.
1. Copy the content of the XML file and and overwrite the sample code or click "_import a file_" to upload the file.

    ![Adding a new definition by uploading one](./managing-workflows/images/01.png)

1. Click the _Save_ button.
1. Click the _Publish_ button.

The newly added definition is ready to be used as a workflow process.

## Editing a Workflow Definition

To edit a workflow:

1. Navigate to the _Control Panel_ &rarr; _Workflow_ &rarr; _Process Builder_.
1. Click the _Actions_ button (![Actions](../../../images/icon-actions.png)) for the workflow then _Edit_.

    ![Editing a definition](./managing-workflows/images/03.png)

Changes to Workflow Definitions are tracked in the _Details_ and _Revision History_ menus.

### Viewing and Restoring Workflow Revisions

To view the workflow definition details and the revision history:

1. Click the *Information* button (![Information](../../../images/icon-information.png))
1. Next to a previous version on the _Revision History_ tab, click the *Actions* button (![Actions](../../../images/icon-actions.png)) and select either *Preview* or *Restore*.

    ![View and restore prior versions of a workflow.](./managing-workflows/images/02.png)

1. When you click *Restore* and see the success message, the prior version is the current version of the workflow. You can now edit the restored version of the workflow.
1. If edits are necessary, edit and click *Update*. This creates another version of the workflow.

## Deleting a Workflow Definition

There are two statuses for a workflow:

* **Published:** Validation is complete, and the workflow can be assigned to assets.
* **Unpublished:** Validation is not performed on the unpublished workflow, and it cannot be assigned to assets until it is published.

Only *Unpublished* workflows can be deleted.

_Published_ workflow definitions cannot be deleted if they are current associated with an asset type. Users have to first deactivate the workflow process and then place the workflow in the _Unpublished_ status.

1. To deactivate an asset type's workflow, reverse the steps found in the [Activating Workflow](./activating-workflow.md) article for each asset type and select _No Workflow_.
1. Navigate to the _Control Panel_ &rarr; _Workflow_ &rarr; _Process Builder_.
1. Click the _Actions_ button (![Actions](../../../images/icon-actions.png)) for the workflow then _Unpublish_.

    ![Unpublishing a Workflow](./managing-workflows/images/04.png)

1. The selected definition is now in the _Unpublished_ section. Click the _Actions_ button (![Actions](../../../images/icon-actions.png)) for the workflow then _Delete_.

    ![Deleting a Workflow](./managing-workflows/images/05.png)

## Configuring Workflow Definition Permissions

In the _System Settings_, you can enable or disable permissions for Regular Administrators to publish workflows and scripts.

![Explicit permission must be granted before administrators are allowed to publish and edit workflow definitions.](./managing-workflows/images/06.png)

## Additional Information

* [Introduction to Crafting XML Workflow Definitions](https://help.liferay.com/hc/articles/360029147791-Introduction-to-Crafting-XML-Workflow-Definitions)
* [Workflow Designer (Subscription)](https://help.liferay.com/hc/articles/360028821892-Workflow-Designer)
