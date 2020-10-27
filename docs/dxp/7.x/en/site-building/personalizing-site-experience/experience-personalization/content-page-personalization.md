# Content Page Personalization and Management

These steps show how to personalize and manage Content Page user experiences based on User Segments.

## Creating a New Content Page Experience

1. Navigate to an existing Content Page or [create a new Content Page](../../creating-pages/building-and-managing-content-pages/building-content-pages.md).
1. At the top of the page, for the *Experience* click on *Default* to open the experience selection dialog.

    ![Click on the current experience to create a new one or select a different existing experience.](./content-page-personalization/images/01.png)

1. Click on *New Experience*.
1. Give it a name and select the User Segment for the audience you want to cater the experience to, or create a new User Segment if your target audience for the Experience is not yet represented by a User Segment.

    ![You can add a new Segment while creating a new Experience.](./content-page-personalization/images/02.png)

1. Modify the page layout and content to display the information you want to show to the selected User Segment.
1. Click *Publish*.

The *Default* version of the page appears for everyone except for members of the selected User Segment, who are presented with a version of the site for their defined User Segment. The example in the figure below creates a new experience for a fictional *Premium Card Prospects* User Segment.

![Your final result for the card prospects might look something like this.](./content-page-personalization/images/03.png)

```note::
  When you create a new experience, it copies the *Default* experience at the time that it is created. Any further changes to the *Default* experience do not effect any of the experiences for that page.
```

## Managing Content Page Personalization

When you edit a Content Page, you can click on the *Experience* to manage the options for that page.

![You can add, edit, delete, or change priority for Experiences.](./content-page-personalization/images/04.png)

1. Go to *Site Administration* &rarr; *Site Builder* &rarr; *Pages*.
1. Click the *Actions* button ![Actions](../../../images/icon-actions.png) &rarr; *Edit* for any Content Page.
1. Click on the *Default* Experience to manage experiences.

From here you have three options:

![Edit](../../../images/icon-edit.png) changes the name or selected User Segment for the Experience.

![Delete](../../../images/icon-delete.png) deletes the Experience.

![Priority](../../../images/icon-priority.png) changes the priority of the Experience. If a User meets the criteria for more than one Experience, the highest ordered one is displayed.

```note::
  Creating new Segments from the New Experience interface is available in Liferay DXP 7.2 Fix Pack 1+ and Liferay Portal 7.2 CE GA2+.
```

## Content Page Personalization and A/B Testing

When you [create an A/B Test](../ab-testing/creating-ab-tests.md) in Liferay DXP, you choose an Experience for the test. This Experience can be the Default one, or any other you have created.

To avoid changes in the Experience that can interfere with the A/B Test results, you cannot edit an Experience that is part of a running A/B Test.

![You cannot edit Experiences that are part of a running A/B Test](./content-page-personalization/images/05.png)

For more information about A/B Testing in Liferay DXP and Liferay Analytics, see [A/B Testing](../ab-testing/ab-testing.md).

## Related Information

* [Personalizing Collections](./personalizing-collection.md)
* [Getting Analytics for User Segments](../segmentation/getting-analytics-for-user-segments.md)
* [Creating User Segments](../segmentation/creating-and-managing-user-segments.md)
