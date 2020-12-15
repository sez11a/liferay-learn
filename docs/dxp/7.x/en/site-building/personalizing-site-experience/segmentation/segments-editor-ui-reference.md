# Segments Editor UI Reference

An overview of the Segments editor UI is discussed here. To access the Segments editor follow these steps:

1. Go to *Site Administration* and select the site that you wish to create segments for.

1. Click *People* &rarr; *Segments* under the Site's menu.

1. Click the *Add User Segment* button (![Add](../../../images/icon-add.png)).

At the top of the editor you can set the name, view the current members of the segment as it is defined, and choose to *Save* the Segment or *Cancel* to discard changes.

![The top portion of the Segment Editor has the segment name and its members.](./segments-editor-ui-reference/images/01.png)

On the right side of the page, there's a *Properties* menu with these options:

- User
- Organization
- Session
- Segments

![You use the Segment Editor to create new Segments.](./segments-editor-ui-reference/images/02.png)

Operations and conjunctions are combined with the properties to define criteria.

## Operations and Conjunctions

Several types of information can be collected by the User Segment interface. Some data is entered in text boxes, while others use selectors to select specific criteria or tools like a date picker. Some fields use an operator, which, depending on the specific context lets you select the relationship between the User or agent data and the criteria:

* *equals*

* *not equals*

* *greater than*

* *greater than or equals*

* *less than*

* *less than or equals*

* *contains*

* *does not contain*

Depending on the nature of the criteria, the operator selection may contain different combinations. For example, the *Date* selection described below contains all the previously listed options except *contains* and *does not contain*, whereas the *Email Address* selection has *equals*, *not equals*, *contains* and *does not contain*.

In between each criteria and each category, you can define an "and" or "or" conjunction. For "and" all criteria must be true in order for the criteria to be satisfied. With "or" it will be true if any of the defined criteria are true. You can also mix operators to create complex cases.

## User Properties

User Properties are user attributes you want to capture. This is made up of user metadata as defined in their accounts, but also contains certain group memberships (like Roles and User Groups) as well as information like the date the user profile was last modified. These User properties are available:

| Property | Description |
| --- | --- |
| Date Modified | Provides a date picker and a relationship selector to select the date that user information was last changed |
| Email Address | Provides a text box to enter the email provided in the user's profile |
| First Name | Enter the first name provided in the user's profile. |
| Group | Select a site that the user is a member of. |
| Job Title | Enter the job title provided in the user's profile. |
| Last Name | Enter the last name provided in the user's profile. |
| Role | Select a Role that the User is a member of. |
| Screen Name | Enter the Users' screen name. |
| Team | Select a Team that the User is a member of. |
| User Group | Select a user group that the User is a member of. |
| User | Select a specific User from a list. |
| Name | The full name of the User. |

Since Liferay DXP 7.2 SP2+, these properties are also available for Users:

| Property | Description |
| --- | --- |
| Date of Birth | Provides a date picker and a relationship selector to select the date of birth of the User |
| Tag | Enter the tags that apply to the Users. |

## Organization Properties

Organization Properties is a selectable list of Organizations to include in your Segment. They contain similar criteria as the User selection, like *Name* and *Date Modified*. These Organization properties are available:

| Property | Description |
| --- | --- |
| Date Modified | Enter the date that the organization information was last modified. |
| Name | Enter the name of the Organization. |
| Hierarchy Path | Enter the name of an ancestor Organization. |
| Organization | Select a specific Organization. |
| Parent Organization | Select a specific parent Organization. |
| Type | Select the type of Organization, if Organization types have been defined. |

Since Liferay DXP 7.2 SP2+, these properties are also available for Organizations:

| Property | Description |
| --- | --- |
| Country | Select a specific country that the Organization is connected to. |
| Region | Select a specific region that the Organization is connected to. |
| Tag | Enter the tags that apply to the Organization. |

## Session Properties

Session Properties contains criteria based on the user's activity, browser, and system information. You can use this to target the user's device or OS, or for activity-based criteria like if a user entered the website through a specific campaign driven landing page. These Session properties are available:

| Property | Description |
| --- | --- |
| Browser | Enter a property from the browser. |
| Cookies | Enter the name of a browser cookie. |
| Device Brand | Enter the brand name of the device being used. |
| Device Model | Enter the model name of the device being used. |
| Device Screen Resolution Height | Enter the screen resolution height value. |
| Device Screen Resolution Width | Enter the screen resolution width value. |
| Language | Select the current Language. |
| Last Sign In Date | Select the date of the User's last sign in. |
| Local Date | Select the current date where the User is located. |
| Referrer URL | Enter the URL that the user last visited. |
| Signed In | Select whether the user is signed in. |
| URL | Enter the current URL. |
| User Agent | Enter a User Agent property. |

Since Liferay DXP 7.2 SP2+, these properties are also available for Organizations:

| Property | Description |
| --- | --- |
| Hostname | Enter a virtual host name. |
| Request Parameters | Enter the name and value of a request parameter. |

## Segments Property

> Available: Liferay 7.3+.

The Segments Property provides a way to combine existing Segments into a new Compound Segment. The Compound Segment inherits the properties from the Segments it is based on, and updates the members automatically. For more information, see [Compound Segments](./creating-and-managing-user-segments.md#compound-segments).