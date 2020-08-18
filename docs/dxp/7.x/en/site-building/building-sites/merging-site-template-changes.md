# Merging Site Template and Site Changes

Once changes are made to a Site created from a Site Template, the Site Template and Site become unlinked. Any changes made to the Site Template from that point on *are not propagated* to the Site.

## Relinking Site to the Site Template

If you want to propagate changes from the Site Template to the Site after they've become unlinked, not to worry. You can relink the Site to the Site Template. Follow these steps:

1. Click the Information icon (![Information](../../images/icon-control-menu-information.png)) that appears in the Control Menu to bring up the Information dialog.
1. Click the *Reset Changes* link in the Information dialog to merge any updates from the Site Template.

    ```warning::
     Resetting changes **reverts any changes made to the page** since the Site and Site Template became unlinked. This can result in changes being unintentionally lost. Proceed with caution.
    ```

If multiple site pages have been modified and you'd like to re-apply the Site Template pages to them, you must click the *Reset Changes* button from each page manually.

![You can click the Information icon to view important information about your Site Template.](./merging-site-template-changes/images/01.png)

## Merging App Preferences from Site Templates

Site Template Administrators can set preferences for apps on Site Template pages. When a Liferay Administrator creates a Site from a Site Template, the app preferences are copied from the Site Template's apps, overriding any default app preferences. When merging Site Template and Site changes (e.g., when resetting), app preferences are copied from Site Template apps to Site apps. Only global app preferences or local app preferences which don't refer to IDs are overwritten.

## Merging App Data from a Site Template

Site Administrators can also add data to Site Template applications. For example, Site Template Administrators can add the Wiki app to a Site Template page and use the Wiki to create lots of articles. When a Liferay Administrator creates a Site from a Site Template, data is copied from the Site Template's apps to the Site's apps. The preferences of the Site's apps are updated with the IDs of the copied data. For example, if a Site is created from a Site Template that has a Wiki app with multiple wiki articles, the wiki articles are copied from the Site Template's scope to the Site's scope and the Site's Wiki app is updated with the IDs of the copied wiki articles.

```important::
  App data (such as Message Board categories), Fragment-based pages, related resources, and permissions on resources are only copied from a Site Template to a Site when that site is *first* created based on the template. No changes made to these entities are propagated to the Site after the Site is created. Neither are such changes propagated to a Site by the *Reset* or *Reset and Propagate* features.
```

## Resolving Site Template Merge Conflicts

In some cases, merging Site Template and Site changes fails. For example, if pages from a Site Template can't be propagated because their friendly URLs are in conflict, Liferay DXP stops the merge after several unsuccessful attempts. To resolve this, follow these steps:

1. Note the indicated merge fail and fix your merge conflicts.
2. After you've resolved the conflicts, open the Product Menu and go to *Configuration* &rarr; *Site Settings* under the Site Menu.
3. Click the *Reset and Propagate* button to reset the merge fail count and attempts to propagate your site's changes again.

```note::
  This process can also be done with Page Template merging, which follows similar steps.
```

![Figure 2: This type of warning is given when there are friendly URL conflicts with Site Template pages.](./merging-site-template-changes/images/02.png)

## Related Information

* [Building Sites with Site Templates](./building-sites-with-site-templates.md)
* [Sharing Site Templates](./sharing-site-templates.md)
* [Site Hierarchies](./site-hierarchies.md)