# Introduction to the Workflow Designer

> Subscribers

 The Workflow Designer is a graphical designer lets users develop their own workflow definitions so their assets go through a review process before publication. By using a convenient drag and drop user interface, users do not need to be familiar with writing XML definitions by hand. Lastly, some of the features can be enhanced Groovy (a supported Java-based scripting language) scripts.

```tip::
   By default, only one workflow definition is installed: the Single Approver Workflow definition.
```

## Building Workflows

To build a new workflow or to upload one, navigate to the _Control Panel_ &rarr; _Workflow_ &rarr; _Process Builder_.

![Navigate to the Workflow > Process Builder to manage workflows.](./introduction-to-the-workflow-designer/images/01.png)

Click the (![Add icon](../../../images/icon-add.png)) to begin.

![Use the intuitive drag and drop to build a workflow.](./introduction-to-the-workflow-designer/images/02.png)

```tip::
   Alternately, users can upload XML scripts then modify the definition using the user interface. To learn more about uploading an XML script, see `Managing Workflows <./managing-workflows.md#uploading-a-new-workflow-definition>`_.
```

![Use the Source tab to upload an XML file.](./introduction-to-the-workflow-designer/images/03.png)

Each Workflow Node represents a specific point in an approval process, whether it is starting the review process, approving or rejecting the asset, or reassigning the task.

Workflow transitions link each node to create the desired flow in the review process. On exiting the first node, processing continues to the next node indicated by the transition.

## Additional Information

* [Managing Workflow with Designer](./workflow-designer/managing-workflow-with-designer.md)
* [Using Forks, Joins, and Conditions](./workflow-designer/using-forks-joins-and-conditions.md)
* [Creating Workflow Tasks](./workflow-designer/creating-workflow-tasks.md)
* [Configuring Workflow Actions and Notifications](./workflow-designer/configuring-workflow-actions-and-notifications.md)
* [Workflow Designer Nodes Reference](./workflow-designer/workflow-designer-nodes-reference.md)
