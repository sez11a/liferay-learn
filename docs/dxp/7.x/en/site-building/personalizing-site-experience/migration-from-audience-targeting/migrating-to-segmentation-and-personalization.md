# Migrating from Audience Targeting to Segmentation and Personalization

Starting Liferay DXP 7.2, Liferay integrates all the Segmentation and Personalization functionality in the core product. You don't longer need to use the Audience Targeting app's functionality, but you must migrate the Audience Targeting segments into the core Liferay Segmentation.

There are three steps in this migration:

1. Upgrade to Liferay DXP 7.2+.
1. Migrate custom rules and display properties.
1. Migrate behavior-based features.

First, upgrade to the latest version of Liferay DXP following the [upgrade guide](../../../installation-and-upgrades/upgrading-liferay/upgrade-basics/upgrade-overview.md). Most of your Audience Targeting configuration is automatically transferred into Liferay Segmentation during the upgrade.

Next, migrate the Audience Targeting custom rules considering the following information:

- Re-evaluate the Audience Targeting custom rules taking into account the Liferay [Segmentation functionality](../segmentation/creating-and-managing-user-segments.md). 
- Some custom rules may have an equivalent. See [Migrating User Segments](./migrating-user-segments.md) for more information.
- For Audience Targeting rules with no equivalent, you need to [migrate these rules manually](./manually-migrating-from-audience-targeting.md).
- If you need to reimplement a rule entirely, follow the information in [Introduction to Segmentation Development](../developer-guide/introduction-to-segmentation-development.md).
- You must also [migrate the display widgets](./manually-migrating-from-audience-targeting.md#migrating-display-properties) since the new Personalization features in Liferay DXP 7.2+ use different tools.  

Finally, you must migrate the behavior-based functionality in Audience Targeting. Starting Liferay DXP 7.2+, you manage behavior-based rules using Analytics Cloud. For more information, see the [Analytics Cloud documentation](https://learn.liferay.com/analytics-cloud/latest/en/individuals-and-segments/segments/segments.html).

## Related Information

- [Migrating User Segments](./migrating-user-segments.md)
- [Manually Migrating from Audience Targeting](./manually-migrating-from-audience-targeting.md)
- [Creating and Managing User Segments](../segmentation/creating-and-managing-user-segments.md)