# Adding a New Organization Type

<!-- I made this a separate article because it it felt like a lot of extra info to have in Creating and Managing Organizations and it seemed lengthy enough to break out into its own article. -->

By default, Liferay DXP only includes the *Organization* type. You can configure the existing type or add additional types using the Organization Type entry in System Settings. There are two main reasons to configure Organization types:

1. Organizations usually correlate to real-life hierarchical structures. Calling them by their real names is helpful for administrators and Users. For example, in a baseball organization, *League*, *Division*, and *Team* Organization types are useful.
1. Enforce control over which Organizations can be top level Organizations and the type of sub-Organization allowed for each parent Organization type. For example, a baseball organization would not allow Division Organization types to be sub-Organizations of Team Organizations.

![Create new organization types through the System Settings entry called Organization Types.](./adding-a-new-organization-type/images/01.png)

Check out the configuration options that configure the default *Organization* type and then configure an additional type.

To add another Organization type called *League*, enter these options into the configuration form:

<!-- table to make it visually easier to follow -->

| header 1 | header 2 | header 3 |
| --- | --- | --- |
| cell 1 | cell 2 | cell 3 |

<!-- Name: *League*
: Adds League to the list of Organization types that appear in the Add
Organization menu.

Country Enabled: *True*
: Enables the Country selection list field on the form for adding and editing
League types.

Country Required: *False*
: Specifies that the *Country* field is not required when adding a League.

Rootable: *True*
: Enables Leagues as a top level Organization. Limit League to sub-Organization
status by excluding this property.

Children Types: *Division*
: Specifies Division as the only allowable sub-Organization type for the League
parent type. -->

Once you configure additional Organization types and click Save, you'll find your new type(s) available for selection in the Add Organization form.

![Custom configuration types are available in the Add Organization form.](./adding-a-new-organization-type/images/02.png)

Users can join or be assigned to Sites when they share a common interest. Users can be assigned to Organizations when they fit into a hierarchical structure. User groups provide a more *ad hoc* way to group Users than Sites and Organizations.
