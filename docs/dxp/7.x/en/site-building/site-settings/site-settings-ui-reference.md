# Site Settings UI Reference

To view a Site's settings, open the Site Menu and go to *Configuration* &rarr; *Settings*, where you'll see the following tabs:

* [General](#general)
* [Social](#social)
* [Language](#language)
* [Advanced](#advanced)

![Site Settings are organized into four tabs.](./site-settings-ui-reference/images/01.png)

```note::
   Many of these settings can be localized to provide translations based on a user's locale. See `Introduction to Localization <https://help.liferay.com/hc/en-us/articles/360028746672-Introduction-to-Localization>`_ and `Modifying Localizable Site Fields <./configuring-site-languages/modifying-localizable-site-fields.md>`_ or more information.
```

## General

General settings range from core configuration, like a Site's Membership Type, to finer details like Documents and Media indexing options. The tab's content is organized into the following subsections: Details, Pages, Categorization, Site URL, Documents and Media, Open Graph, Asset Auto Tagging, Sharing, and Custom Fields.

### Details

**Site ID**: A unique number automatically generated for a Site at its creation. This ID is permanent and cannot be changed.

**Name**: Set a Site's title using the *Name* field. This title is displayed in the browser's title bar as well as the header for each Site page. You can also localize a Site's name via the *Language Flag* button.

**Description**: Use the *Description* field to explain a Site's purpose. You can also localize a Site's description via the *Language Flag* button.

**Active**: Determine whether a Site is *Active* or *Inactive*. While inactive, a site is inaccessible to users, though it can be reactivated if desired.

**Membership Type**: A *Membership Type* determines how restrictive a Site's membership is. See [Changing Site Membership Types](./site-users/changing-site-membership-type.md) for more information.

**Allow Manual Membership Management**: Determine whether to allow members to be manually added or removed from a Site. If your Site's membership is handled automatically by a membership policy, you can disable this setting. See [Managing Membership Policies for Sites](./site-users/changing-site-membership-type.md) for more information.

**Parent Site**: Designate a Site as a child Site by selecting its parent Site. See [Site Hierarchies](../building-sites/site-hierarchies.md) for more information.

**Limit Membership to Parent Site Members**: Determine whether a child Site's membership is limited to members of its parent Site. This option only appears for child Sites.

![In the Details section you can view and edit a site's name, description, status, and more.](./site-settings-ui-reference/images/02.png)

### Pages

Here you can view a Site's Public and Private pages, if any exist. If they don't exist, a *Site Templates* selector appears for creating pages with a template.

![In the Pages section, you can view a Site's public and private pages.](./site-settings-ui-reference/images/03.png)

### Custom Fields

Here you can view and configure any custom fields you've defined for your pages. With these fields, you can set page metadata, such as author, date of creation, and geolocation. This section only appears once you've configured custom fields for your Site. See [Custom Fields](../../system-administration/configuring-liferay/adding-custom-fields.md) for more information.

### Categorization

Here you can categorize Site content using *Topics* and *Tags*. These tools help organize sites so users can more easily find your site's content via search and navigation widgets. See [Organizing Content with Tags and Categories](../../content-authoring-and-management/index.html#tags-and-categories) for more information.

![You can categorize Site content using Topics and Tags.](./site-settings-ui-reference/images/04.png)

### Site URL

**Friendly URL**: Set a custom URL for both public and private Site pages. See [Configuring Your Site's Friendly URL](./managing-site-urls/configuring-your-sites-friendly-url.md) for more information.

**Public and Private Virtual Host**: Set public and private *Virtual Hosts* to map to a Site's public and private pages. See [Configuring Virtual Hosts Site URLs](./managing-site-urls/configuring-virtual-hosts-site-urls.md) for more information.

![In the Site URL section, you can configure your Site's Friendly URL, as well as Virtual Hosts for your public and private pages.](./site-settings-ui-reference/images/05.png)

### Documents and Media

You can enable *Directory Indexing* for a Site. When enabled, a User with view permission can browse the Site's document library files and folders.

![You can determine whether to enable Directory Indexing for a Site.](./site-settings-ui-reference/images/06.png)

### Site Template

If you created your Site using a *Site Template*, it's displayed here, along with whether the template allows Users to modify pages that inherit it. See [Building Sites from Templates](../building-sites/building-sites-with-site-templates.md) for more information about Site templates and how to create your own.

![View your page's selected template.](./site-settings-ui-reference/images/07.png)

### Open Graph

**Enable Open Graph**: Determine whether to embed [Open Graph](https://ogp.me/) `<meta>` tags in the `<head>` of your Site's pages. These tags define page metadata to create engaging representations of your Site's content when shared in applications that support the Open Graph protocol, such as Facebook, Slack, and Twitter.

**Open Graph Image**: Use the *Image* and *Alt Description* fields to define the default `og:image` and `og:image:alt` properties used for Site pages. You can also localize the image's alt description via the *Language Flag* button. See [Configuring Open Graph](./configuring-open-graph.md) to learn more.

![You can enable or disable Open Graph, define image meta tags, and see an image preview.](./site-settings-ui-reference/images/08.png)

### Sharing

Here you can enable or disable document sharing between Site Users. When enabled, Users can share items with other users. See [Sharing Documents with Other Users](../../content-authoring-and-management/documents-and-media/publishing-and-sharing/managing-document-access/sharing-documents-with-other-users.md) for more information. 

![You can enable or disable document sharing between Site Users.](./site-settings-ui-reference/images/10.png)

### Asset Auto Tagging

Here you can enable or disable asset auto tagging for a Site. When enabled, assets are auto tagged by the providers configured at instance level. See [Asset Auto Tagging](../../content-authoring-and-management/web-content/auto_tagging.md) for more information. 

![You can enable or disable asset auto tagging for a Site.](./site-settings-ui-reference/images/09.png)

## Social

Here you can manage the social interactions between Users on a Site.

### Ratings

Here you can select the type of ratings used for the following Site applications: Comments, Knowledge Base, Blogs, Wiki, Message Boards, Web Content, and Documents and Media. See [Configuring Content Ratings Types](./site-content-configurations/configuring-content-rating-type.md) for more information.

![You can select the type of ratings used for Site applications.](./site-settings-ui-reference/images/11.png)

### Mentions

Here you can enable Users mentioning other Users in Site applications. See [Mentioning Users](../../collaboration-and-social/notifications-and-requests/user-guide/configuring-mentions.md) for more information.

![You can determine whether Users can mention other Users in Site applications.](./site-settings-ui-reference/images/12.png)

## Language

Here you can choose to use the installation's default language or define this Site's enabled languages.

![You can update language options through the Languages tab of Site Settings.](./site-settings-ui-reference/images/13.png)

## Advanced

Here you can configure additional settings, including analytics services, content sharing, and more.

### Default User Associations

Here you can select the Roles and Teams that new Site members are assigned to by default. See [Configuring Role and Team Defaults for Site Members](./site-users/configuring-role-and-team-defaults-for-site-members.md) for more information.

![You can select the Roles and Teams that new Site members are assigned to by default.](./site-settings-ui-reference/images/14.png)

### Analytics

Here you can set the Google Analytics ID, create method configuration, and custom options used for a site. You can also set the Piwik Analytics tracking code for a Site by entering the full script code, including the script start and end tags.

If you require a different analytics service, you can add it. See [Adding a New Analytics Service](./adding-a-new-analytics-service.md) for more information and how to add additional fields for different analytics services.

![In the Analytics section, you can configure a Site's analytic services.](./site-settings-ui-reference/images/15.png)

### Maps

Here you can select the maps API provider to use when displaying geolocalized assets in your Site. Geolocalized assets can be displayed for documents, web content articles, DDL records, and more. See [Geolocating Assets](./site-content-configurations/configuring-geolocation-for-assets.md) for more information.

![You can select the maps API provider used for Site geolocation.](./site-settings-ui-reference/images/16.png)

### Recycle Bin

Here you can enable the Recycle Bin and specify the number of minutes an asset remains in the Recycle Bin before being automatically deleted. See [Configuring the Asset Recycle Bin for Sites](./site-content-configurations/configuring-the-asset-recycle-bin-for-sites.md) for more information.

![You can configure a Site's Recycle Bin.](./site-settings-ui-reference/images/17.png)

### Content Sharing

Here you can determine whether child Sites can display content from this Site, including structures, templates, categories, widget templates, and more. Disabling this option immediately revokes content sharing from all child Sites. See [Managing Content Sharing Across Sites](./site-content-configurations/managing-content-sharing-globally.md) for more information. 

![You can determine whether child Sites can display content from this Site.](./site-settings-ui-reference/images/18.png)
