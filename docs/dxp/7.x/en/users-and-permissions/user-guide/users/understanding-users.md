# Understanding Users

Every person who accesses a Liferay site is considered a User. Users who are not authenticated are considered "Guest" users. Liferay ships out-of-the-box with a default Admin user who has complete control and access to the system. [Roles and Permissions](link) govern what users are able to see and do on a site.

Users break down into three general categories:

| User | Default Access | Notes |
| --- | --- | --- | --- |
| Administrator | Liferay Admin with full system access | Default Admin user <br> Email: `test@liferay.com` <br> Password: `test` <br> We *strongly* recommend that users change the password for this account promptly after installation. |
| Guest | View access to Public Pages and Sites. In most cases cannot create or add content unless explicitly permitted to. | By default, Guest users are able to create an account on a Liferay site to view and interact with *Public* sites. |
| User | View access to Pages and Sites they are members of. Able to create content. | Placeholder notes. |

## Managing Users

Users are managed in the *Users* section of the Control Panel.

![Managing Users in the Users and Organizations section of the Control Panel.](./introduction-to-users/images/01.png)

Managing user metadata (name, department, etc.), permissions, and activation status can be done through this section.

### Adding Users

Users can be added by an administrator using the Users and Organizations section of the Control Panel or [through the API](future-article?). New users can also be created if open registration is configured or by syncing your Liferay installation with an LDAP server. See [Adding, Editing, or Removing Users]() to learn more.

#### Open Registration

By default, guest users are able to create user accounts.

See [this article]() to learn more about configuring registration and authentication for users.

#### Configuring a User Directory

Organizations that manage users with LDAP can sync their LDAP server with Liferay to import users. See [this article]() to learn more.

<!-- #### Other Methods

Are there other methods of adding users? -->

## Managing User Access

User access is controlled by Roles and Permissions. Permissions define what a user can and cannot do. Roles are groupings of permissions. Permissions can be configured specifically for individual users, but is more effectively done by defining permissions for a role and assigning users to pre-set roles. To learn more about managing access for users, see [Roles and Permissions](blah).

## Organizing Users

Liferay has several tools to help users organize and administer users.

[Organizations](link-to-organizations-article) are an entity in Liferay that can group users in a distributed hierarchy. Practically, this means that large organizations can empower and delegate users to administer their organizational groups.

![An example of how an organizational hierarchy can be created in Liferay.](introduction-to-users/images/02.png)

[User Groups](link-to-user-groups-article) are another method available for grouping users to simplify assignment of roles and administration. The main distinction between a User Group and an Organization is that a User Group is a more informal and ad-hoc tool for grouping.

## Related Information

* [Adding and Managing Users]()
* [Organizations]()
* [User Groups]()
