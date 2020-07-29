# Assigning Roles to User Segments

> Available: Liferay DXP 7.2 SP2+, Liferay CE 7.3.1 GA2

User Segments are dynamically assigned User collections. If a [Segment can be well-defined with the available criteria](../../site-building/personalizing-site-experience/segmentation/creating-and-managing-user-segments.md), the administrative overhead of User management can be greatly reduced. On top of this, you can define [personalized experiences](../../site-building/personalizing-site-experience/personalizing-site-experience.md) for User Segments created at the Site scope, ensuring that Users see the most relevant content in your Site.

| Where Do I Create User Segments? | What Are They For? | Where Do I Assign Roles To a Segment? |
| --------------------------------------------------------------------------- | -------------------------------------------------------------- | --------------------- |
| Site Segments (Site Menu &rarr; People &rarr; Segments)                     | Create personalized experiences for a Site's Users             | Site Menu &rarr; People &rarr; Segments (Actions) |
| Instance Segments (Control Panel &rarr; Users &rarr; Roles (Regular Roles)) | Efficiently and dynamically manage Users and their permissions | Control Panel &rarr; Users &rarr; Roles (Regular Roles) |

## Assigning a Regular Role to a User Segment

> Available: Liferay DXP 7.2 SP2+

Regular Roles can be assigned to User Segments created at the Global scope. To assign Regular Roles to a User Segment,

1. Open the Product Menu and go to *Control Panel* &rarr; *Users* &rarr; *Roles*.

1. Open the Actions Menu (![Actions](../../images/icon-actions.png)) next to the Regular Role and select *Edit*.

    ![Edit a Role to assign the User Segment to it.](./assigning-roles-to-user-segments/images/01.png)

1. Select the *Assignees* &rarr; *Segments* tab, and click the (![Add Button](../../images/icon-add.png)) to select a User Segment.

    ![Go to the Segments tab under Assignees to assign the Role.](./assigning-roles-to-user-segments/images/02.png)

1. Check the box next to an existing User Segment and click *Add* to assign the Role to it. If the User Segment doesn't exist, click the (![Add Button](../../images/icon-add.png)) to create a new User Segment.

    ![Check the box next to the User Segment you want to assign the Role to.](./assigning-roles-to-user-segments/images/03.png)

The selected User Segment(s) appear(s) in the list of assignees for the Role.

![The User Segment appears under the list of Assignees for the Role.](./assigning-roles-to-user-segments/images/04.png)

## Assigning a Site Role to a User Segment

> Available: Liferay DXP 7.2 SP3+

[Site Roles](./understanding-roles-and-permissions.md) can be assigned to Segments, with two exceptions: Site Administrator and Site Owner.

1. Open the Product Menu and go to *People* &rarr; *Segments* in the Site Menu.

1. Open the Segment's Actions menu (![Actions](../../images/icon-actions.png)) and click _Assign Site Roles_.

1. Assign Site Roles to the Segment, then click _Add_.

   ![Site Roles can be assigned to Segments.](./assigning-roles-to-user-segments/images/05.png)

## Related Information

* [Creating User Segments](../../site-building/personalizing-site-experience/segmentation/creating-and-managing-user-segments.md)
