# Configuring Workflow Actions and Notifications

Users can add an action and a notification to each node, including the Start and End nodes. The Start and End nodes can usually be deployed with little or no configurations required, but you can always add a notification to alert others that the review process has begun or that the asset has been approved (or rejected).

## Adding Actions

Actions do additional processing before entering the node, after exiting a node, or once a task node is assigned.

To add an action to a node:

1. Navigate to the _Control Panel_ &rarr; _Workflow_ &rarr; _Process Builder_.
1. Click the _Workflows_ tab.
1. Click on the Workflow definition (for example, _Single Approver_).
1. Click on a node.
1. Double click _Actions_ to define an action.

    ![You can add an Action to a Task node.](./configuring-workflow-actions-and-notifications/images/01.png)

1. Enter the script in the _Script_ field. The Single Approver workflow contains an Update task with an action written in Groovy that sets the status of the asset as _denied_, then sets it to _pending_.

    ```java
        import com.liferay.portal.kernel.workflow.WorkflowStatusManagerUtil;
        import com.liferay.portal.kernel.workflow.WorkflowConstants;

        WorkflowStatusManagerUtil.updateStatus(WorkflowConstants.getLabelStatus("denied"), workflowContext);
        WorkflowStatusManagerUtil.updateStatus(WorkflowConstants.getLabelStatus("pending"), workflowContext);
    ```

1. Click _Save_ when finished.

In this example, the action script first sets the status to _denied_ and then to _pending_ because for some assets, the _denied_ status sends the asset creator an email notification that the item has been denied.

The end node in your workflow definition has an action configured on it by default, on entry to the end node:

```java
    import com.liferay.portal.kernel.workflow.WorkflowStatusManagerUtil;
    import com.liferay.portal.kernel.workflow.WorkflowConstants;

    WorkflowStatusManagerUtil.updateStatus(WorkflowConstants.getLabelStatus("approved"), workflowContext);
```

This is a Groovy script that updates the status to _approved_.

## Adding Notifications

Workflow Notifications are sent to tell task assignees that the workflow needs attention or to update asset creators on the status of the process. They can be sent for tasks or any other type of node in the workflow.

To set up notifications, double click on _Notifications_ in a node's Properties tab.

 ![Notifications are found in the Properties tab.](./configuring-workflow-actions-and-notifications/images/03.png)

1. Enter the following:
    * **Name:** Update Notification
    * **Description**: Enter a description for this notification

1. Select _Freemarker_ from the _Template Language_ dropdown menu.
1. Enter a message in the _Template_ field:
    * `${userName} sent you a ${entryType} for review in the workflow.`

1. Select the Notification Type; this field is multiple-select so you can multiple users.
1. Select _On Assignment_ from the _Execution Type_ dropdown menu. This indicates when the notification is sent.
1. Select a recipient type.

     ![You can add an Notification to a Task node.](./configuring-workflow-actions-and-notifications/images/02.png)

1. Click _Save_ when finished.

## Additional Information

* [Using the Task Node](./using-the-task-node.md)
* [Workflow Designer Nodes Reference](./workflow-designer-nodes-reference.md)
