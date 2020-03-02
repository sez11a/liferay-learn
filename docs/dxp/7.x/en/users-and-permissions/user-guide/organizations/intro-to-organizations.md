# Organizations

Liferay Organizations can mirror real organizations and nest to unlimited levels. Consider a typical organization chart: 

![An organization chart shows how a typical grouping of company, governmental department, non profit, or any other collection of people is organized. Liferay's Organizations can model these.](./intro-to-organizations/images/01.png)

This structure can be modeled in Liferay DXP, and this empowers you to delegate administrative responsibility and place Users exactly where they belong in your organization. If you apply permissions to those Users through Organization Roles, Users who move to different Organizations automatically receive permissions appropriate to them. 

Interested? There's more. 

### What can Organization Administrators Do?

Organization administrators are *delegated* administrators. This means they get a piece of your portal---a piece you define---to take some of the load of administration off of your shoulders. Organization administrators can do these things: 

- Manage all the Users in their Organization and in any child Organization. 
- Optionally, assign Users to their Organization (see the `Organizations` [section of the portal-ext.properties](https://docs.liferay.com/portal/7.2-latest/propertiesdoc/portal.properties.html#Organizations)).
- Manage an Organization's Site, just like a Site Administrator. 

Organization administrators can't access the Control Panel by default. Instead, they can click the *My Organizations* link to gain access to any Organizations they manage.

![Organization administrators can access their Organizations from their profile.](./intro-to-organizations/images/02.png)

### Organization Roles and Permissions

Organizations can have Roles scoped only to them. An Organization Role's permissions affect only applications and content within that Organization. For example, the difference between a portal-scoped Role versus an Organization-scoped Role that define access to Message Boards is that the Organization Role affects only Message Boards in that Organization (or its children), while the portal Role affects all Message Boards. 

This has great benefits. If, for example, a large company has modeled its organization chart in Liferay DXP as Organizations, administration becomes an easy task. An IT User who has permission to use IT's provisioning application can transfer to HR, and by moving that User from IT to HR, permissions to the provisioning tool are removed automatically, while permissions to HR's compensation tool are granted automatically. 

### Organization Sites

If an Organization has an attached Site, the Organization's administrators are treated as the Site administrators: they can manage the Site's pages, portlets, content, and Users. Members of an Organization with an attached Site are members of the Organization's Site. This means that they can access the private pages of the Organization's Site, along with any portlets or content there. Attaching Sites to Organizations allows portal administrators to use Organizations to facilitate distributed portal administration, not just distributed User administration. 

