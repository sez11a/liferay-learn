# 7.3 Breaking Changes

This document presents a chronological list of changes that break existing functionality, APIs, or contracts with third party Liferay developers or users. We try our best to minimize these disruptions, but sometimes they are unavoidable.

Here are some of the types of changes documented in this file:

* Functionality that is removed or replaced
* API incompatibilities: Changes to public Java or JavaScript APIs
* Changes to context variables available to templates
* Changes in CSS classes available to Liferay themes and portlets
* Configuration changes: Changes in configuration files, like `portal.properties`, `system.properties`, etc.
* Execution requirements: Java version, J2EE Version, browser versions, etc.
* Deprecations or end of support: For example, warning that a certain feature or API will be dropped in an upcoming version.

## Breaking Changes List

### Liferay FontAwesome Is No Longer Included by Default
- **Date:** 2019-Aug-21
- **JIRA Ticket:** [LPS-100021](https://issues.liferay.com/browse/LPS-100021)

#### What changed?

Liferay FontAwesome, which included icon fonts for Font Awesome, Glyphicon, and custom Liferay icons, is no longer included by default.

#### Who is affected?

This affects pages or sites that have a Theme applied that does not include these icon fonts itself. Any content or code on such pages or sites that uses these icon fonts will no longer work.

#### How should I update my code?

Depending on how you're using icon fonts, there's a few approaches you can take.

##### For liferay-ui:icon usage

Replace `<liferay-ui:icon iconCssClass="icon-user">` with `<liferay-ui:icon icon="user" markupView="lexicon" />`

##### For JavaScript-generated icons

Those manually generating FontAwesome icon html can use the `Liferay.Util.getLexiconIconTpl('user')` API. For example, the previous call would return the html code for a user svg icon.

##### For direct HTML within JSPs

Developers directly using icons in jsps can either use the `liferay-ui:icon` tag as explained above or the `clay:icon` one to generate svg-based icons instead.

##### For non-controlled code

If you don't have access to the content that uses the icon fonts or you don't want to update the code or content, you can include the fonts in your Themes.

During the 7.2 upgrade process, the theme upgrade assistant prompts developers to keep FontAwesome as part of the Theme. Themes that already include the icon fonts won't be affected and will continue to work in 7.3.

#### Why was this change made?

This change was made to save bandwidth and increase performance of your sites by not serving unnecessary files.

---------------------------------------

### Removed liferay.frontend.ProgressBar
- **Date:** 2019-Aug-28
- **JIRA Ticket:** [LPS-100122](https://issues.liferay.com/browse/LPS-100122)

#### What changed?

The legacy metal+soy `liferay.frontend.ProgressBar` component, used as a temporary bridge for legacy behaviour, was removed.

#### Who is affected?

This affects any code that relies on `liferay.frontend.ProgressBar`; this is usually done via `soy` as `{call liferay.frontend.ProgressBar /}`.

#### How should I update my code?

There's no direct replacement for the `liferay.frontend.ProgressBar` component. If you have a component that relies on it, you can co-locate a copy of the old implementation and use it locally within your module.

#### Why was this change made?

The `liferay.frontend.ProgressBar` component was deprecated in 7.2 and is no longer used.

---------------------------------------

### Removed liferay.frontend.Slider
- **Date:** 2019-Oct-10
- **JIRA Ticket:** [LPS-100124](https://issues.liferay.com/browse/LPS-100124)

#### What changed?

The legacy metal+soy `liferay.frontend.Slider` component, used as a temporary bridge for legacy behaviour, was removed.

#### Who is affected?

This affects any code that relies on `liferay.frontend.Slider`; this is usually done via `soy` as `{call liferay.frontend.Slider /}`.

#### How should I update my code?

There's no direct replacement for the `liferay.frontend.Slider` component. If you have a component that relies on it, you can co-locate a copy of the old implementation and use it locally within your module.

#### Why was this change made?

The `liferay.frontend.Slider` component was deprecated in 7.2 and is no longer used.

---------------------------------------

### Removed com.liferay.asset.taglib.servlet.taglib.soy.AssetTagsSelectorTag
- **Date:** 2019-Oct-15
- **JIRA Ticket:** [LPS-100144](https://issues.liferay.com/browse/LPS-100144)

#### What changed?

The Java class `com.liferay.asset.taglib.servlet.taglib.soy.AssetTagsSelectorTag` was removed.

#### Who is affected?

This affects any code that directly instantiates or extends this class.

#### How should I update my code?

There's no direct replacement for the removed class. If you have code that depends on it, you must copy over the old implementation to your own project and change the dependency to rely on your local version.

#### Why was this change made?

The `asset:asset-tags-selector` and its components have been migrated to React, making the old tag and its soy infrastructure unnecessary.

---------------------------------------

### Removed Portal Property user.groups.copy.layouts.to.user.personal.site
- **Date:** 2019-Dec-26
- **JIRA Ticket:** [LPS-106339](https://issues.liferay.com/browse/LPS-106339)

#### What changed?

The portal property `user.groups.copy.layouts.to.user.personal.site` and the behavior associated with it were removed.

#### Who is affected?

This affects anyone who set the `user.groups.copy.layouts.to.user.personal.site` property to `true` to copy User Group pages to User Personal Sites.

#### How should I update my code?

There's no direct replacement for this property. If you depend on the behavior, you can copy the old implementations of `UserGroupLocalServiceImpl#copyUserGroupLayouts` to your own project.

#### Why was this change made?

The behavior associated with this property has been deprecated since 6.2.

---------------------------------------

### Removed Support for Auto Deploying EXT Plugins
- **Date:** 2019-Dec-31
- **JIRA Ticket:** [LPS-106008](https://issues.liferay.com/browse/LPS-106008)

#### What changed?

The support for deploying EXT plugins using Auto Deployer (via `liferay-home/deploy folder`) was removed. EXT plugins copied to the deploy folder are no longer recognized.

#### Who is affected?

This affects anyone deploying EXT plugins via the Auto Deployer.

#### How should I update my code?

There's no direct replacement for the removed feature. If you have an EXT plugin, you must deploy it manually or use [`ant direct-deploy`](https://github.com/liferay/liferay-plugins-ee/blob/7.0.x/ext/build-common-ext.xml#L211).

#### Why was this change made?

This feature has been deprecated since 7.1.

---------------------------------------

### Replaced OSGi configuration Property autoUpgrade
- **Date:** 2020-Jan-03
- **JIRA Ticket:** [LPS-102842](https://issues.liferay.com/browse/LPS-102842)

#### What changed?

The OSGi property `autoUpgrade` defined in `com.liferay.portal.upgrade.internal.configuration.ReleaseManagerConfiguration.config` was replaced with the portal property `upgrade.database.auto.run`.

Unlike the old property, which only controlled the upgrade processes in modules, the new one also affects the Core upgrade processes. The default value is `false`, so upgrade processes won't run on startup or module deployment. You can execute module upgrade processes anytime via Gogo console.

#### Who is affected?

This affects development environments where you don't want to run the upgrade when a new process is deployed. This property can't be set to `true` in production environments. In these cases, you must use the upgrade tool to execute minor and major schema version changes.

#### How should I update my code?

This change doesn't affect your code.

#### Why was this change made?

This change was made to unify the auto-upgrade feature between the Core and modules. The default value has also changed to avoid the execution of new upgrade processes on startup in production environments.

---------------------------------------

### Removed Cache Bootstrap Feature
- **Date:** 2020-Jan-8
- **JIRA Ticket:** [LPS-96563](https://issues.liferay.com/browse/LPS-96563)

#### What changed?

The cache bootstrap feature has been removed. These properties can no longer be used to enable/configure cache bootstrap:

`ehcache.bootstrap.cache.loader.enabled`,
`ehcache.bootstrap.cache.loader.properties.default`,
`ehcache.bootstrap.cache.loader.properties.${specific.cache.name}`.

#### Who is affected?

This affects anyone using the properties listed above.

#### How should I update my code?

There's no direct replacement for the removed feature. If you have code that depends on it, you must implement it yourself.

#### Why was this change made?

This change was made to avoid security issues.

---------------------------------------

### Removed liferay-frontend:cards-treeview Tag
- **Date:** 2020-Jan-10
- **JIRA Ticket:** [LPS-106899](https://issues.liferay.com/browse/LPS-106899)

#### What changed?

The `liferay-frontend:cards-treeview` tag was removed.

#### Who is affected?

This affects anyone using the tag from a jsp, or some of its components inside a SOY (Closure Templates) template.

#### How should I update my code?

There's no direct replacement for the removed feature. If you have code that depends on it, you must implement it yourself.

#### Why was this change made?

This change was made because the tag was primarily used internally.

---------------------------------------

### Removed liferay-frontend:contextual-sidebar Tag
- **Date:** 2020-Jan-10
- **JIRA Ticket:** [LPS-100146](https://issues.liferay.com/browse/LPS-100146)

#### What changed?

The `liferay-frontend:contextual-sidebar` tag was removed.

#### Who is affected?

This affects anyone using the tag from a jsp or some of its components inside a SOY (Closure Templates) template.

#### How should I update my code?

There's no direct replacement for the removed feature. If you have code that depends on it, you must implement it yourself.

#### Why was this change made?

This change was made because the tag was primarily used internally.

---------------------------------------

### Changed Control Menu and Product Menu Positioning
- **Date:** 2020-Feb-04
- **JIRA Ticket:** [LPS-107487](https://issues.liferay.com/browse/LPS-107487)

#### What changed?

The placement and structure of the Control and Product Menus has changed to address several accessibility concerns and common visual glitches.

These changes have been applied to the Control and Product menus:
- The Product Menu has been moved outside of the Control Menu
- The Control Menu now uses `position:sticky` to control its behaviour
- Styles of the menus inside the Control Menu have been updated to account for the new sticky behaviour

#### Who is affected?

This could affect developers that have a custom Control Panel Theme with a customized `portlet.ftl` template or those that have developed a custom menu that behaves as a sticky bar and is included using the `*ControlMenuEntry` API.

#### How should I update my code?

##### Control Panel Themes

Developers with custom Control Panel themes should move the call (if any) to the `@liferay.control_menu` macro above the portlet section in their `portlet.ftl`.

**Before:**

```markup
<section class="portlet" id="portlet_${htmlUtil.escapeAttribute(portletDisplay.getId())}">
	${portletDisplay.writeContent(writer)}
</section>

<#if portletDisplay.isStateMax()>
	<@liferay.control_menu />
</#if>
```

**After:**

```markup
<#if portletDisplay.isStateMax()>
	<@liferay.control_menu />
</#if>

<section class="portlet" id="portlet_${htmlUtil.escapeAttribute(portletDisplay.getId())}">
	${portletDisplay.writeContent(writer)}
</section>
```

##### Custom Sticky Bars

Developers with custom Sticky Bars included using the `*ControlMenuEntry` API can use the newly included extension points in the Control Menu to inject their components.

Move the code that injects the menu to a `DynamicInclude` component and register it for the proper position:
- Before the Control Menu: Use `com.liferay.product.navigation.taglib#/page.jsp#pre`
- After the Control Menu: Use `com.liferay.product.navigation.taglib#/page.jsp#post`

#### Why was this change made?

This change was made to improve accessibility and simplify the required logic for positioning and controlling top-positioned menus. It provides a more correct and expected markup that avoids common visual glitches.

---------------------------------------

### jQuery Is No Longer Included by Default
- **Date:** 2020-Feb-04
- **JIRA Ticket:** [LPS-95726](https://issues.liferay.com/browse/LPS-95726)

#### What changed?

Previously, `jQuery` was being included on every page by default and made available through the global `window.$` and the scoped `AUI.$` variables. After this change, `jQuery` is no longer included by default and those variables are `undefined`.

#### Who is affected?

This affects any developer who used `AUI.$` or `window.$` in their custom scripts.

#### How should I update my code?

Use any of the strategies to add third party libraries to provide your own version of JQuery to use in your code.

Additionally, as a temporary measure, you can bring back the old behaviour by setting the `Enable jQuery` property in *System Settings* &rarr; *Third Party* &rarr; *jQuery* to `true`.

#### Why was this change made?

This change was made to avoid bundling and serving additional library code on every page that was mostly unused and redundant.

---------------------------------------

### Server-side Parallel Rendering Is No Longer Supported
- **Date:** 2020-Mar-16
- **JIRA Ticket:** [LPS-110359](https://issues.liferay.com/browse/LPS-110359)

#### What changed?

Properties with the prefix `layout.parallel.render` were removed, which means parallel rendering is only supported when AJAX rendering is enabled.

#### Who is affected?

This affects anyone using the removed properties.

#### How should I update my code?

Remove any properties prefixed with `layout.parallel.render` from your properties file.

#### Why was this change made?

This feature has been deprecated.

---------------------------------------

### Simple Editor Is No Longer Bundled by Default
- **Date:** 2020-Mar-27
- **JIRA Ticket:** [LPS-110734](https://issues.liferay.com/browse/LPS-110734)

### What changed?

As of 7.3, CKEditor is the default and only supported WYSIWYG editor.

### Who is affected

This affects anyone who uses the Liferay Front-end Editor Simple Web module.

### How should I update my code?

If you've configured Liferay Portal to use the Simple Editor, you can remove these configurations. If you still want to use the Simple Editor, you must take these steps:

- Keep your configurations.
- Open https://repository.liferay.com/nexus/index.html in your browser.
- Search for `com.liferay.frontend.editor.simple.web`.
- Download a .jar file for the `com.liferay.frontend.editor.simple.web` module.
- Deploy the .jar file you downloaded in your liferay-portal installation.

#### Why was this change made?

This change was made to consolidate all our UX for writing Rich Text Content around a single Editor to provide a more cohesive and comprehensive experience.

---------------------------------------

### TinyMCE Editor Is No Longer Bundled by Default
- **Date:** 2020-Mar-27
- **JIRA Ticket:** [LPS-110733](https://issues.liferay.com/browse/LPS-110733)

### What changed?

As of 7.3, CKEditor is the default and only supported WYSIWYG editor.

### Who is affected

This affects anyone who uses TinyMCE.

### How should I update my code?

If you've configured Liferay Portal to use the TinyMCE, you can remove these configurations. If you still want to use TinyMCE, you must take these steps:

- Keep your configurations.
- Open https://repository.liferay.com/nexus/index.html in your browser.
- Search for `com.liferay.frontend.editor.tinymce.web`.
- Download a .jar file for the `com.liferay.frontend.editor.tinymce.web` module.
- Deploy the .jar file you downloaded in your liferay-portal instalation.

#### Why was this change made?

This change was made to consolidate all our UX for writing Rich Text Content around a single Editor to provide a more cohesive and comprehensive experience.

---------------------------------------

### asset.vocabulary.default Now Holds a Language Key
- **Date:** 2020-Apr-28
- **JIRA Ticket:** [LPS-112334](https://issues.liferay.com/browse/LPS-112334)

### What changed?

`asset.vocabulary.default` is now a language key and no longer has a fixed value of `Topic`.

### Who is affected

This affects anyone who overwrites the property.

### How should I update my code?

There is no need to change the code if the property is not overwritten. If the property was overwritten and the specified key is not found, the provided text will be taken as the name of the default vocabulary.

#### Why was this change made?

The change was made so users don't have to change the name for the default vocabulary in all languages.

---------------------------------------

### Liferay.Poller Is No Longer Initialized by Default
- **Date:** 2020-May-19
- **JIRA Ticket:** [LPS-112942](https://issues.liferay.com/browse/LPS-112942)

#### What changed?

The global AUI `Liferay.Poller` utility is now deprecated and is no longer
initialized by default.

#### Who is affected?

This affects any code that relies on `Liferay.Poller`; this is usually done via
a call to `Liferay.Poller.init()` in a JSP.

#### How should I update my code?

There's no direct replacement for the `Liferay.Poller` utility. If you must
initialize `Liferay.Poller`, update your JSP to use the code below:

```markup
<%@ page import="com.liferay.petra.encryptor.Encryptor" %>

<%-- For access to `company` and `themeDisplay`. --%>
<liferay-theme:defineObjects>

<aui:script use="liferay-poller">
	<c:if test="<%= themeDisplay.isSignedIn() %>">
		Liferay.Poller.init({
			encryptedUserId:
				'<%= Encryptor.encrypt(company.getKeyObj(), String.valueOf(themeDisplay.getUserId())) %>',
		});
	</c:if>
</aui:script>
```

#### Why was this change made?

The `Liferay.Poller` component was only used in the Chat application, which is
archived. Skipping initialization by default streamlines page loads for the
common case.

---------------------------------------

### ContentTransformerListener Is Disabled By Default
- **Date:** 2020-May-25
- **JIRA Ticket:** [LPS-114239](https://issues.liferay.com/browse/LPS-114239)

#### What changed?

`ContentTransformerListener` is now disabled by default.

#### Who is affected?

This affects Liferay Portal installations using legacy web content features
provided by the `ContentTransformerListener`, such as embedding web content
inside another web content, a legacy edit in place infrastructure, token
replacements (`@article_group_id@`, `@articleId;elementName@`), etc.

#### How should I update my code?

There's no need to update your code. If you still want to use
`ContentTransformerListener`, you can enable it in System Settings via the
*Enable ContentTransformerListener* property under *Content & Data* &rarr; *Web
Content* &rarr; *Virtual Instance Scope* &rarr; *Web Content*.

#### Why was this change made?

`ContentTransformerListener` was disabled to improve performance, due to its
expensive string processing on article elements (calling
`HtmlUtil.stripComments` and `HtmlUtil.stripHtml` on article fields).

---------------------------------------

### Liferay.BrowserSelectors.run Is No Longer Called
- **Date:** 2020-May-26
- **JIRA Ticket:** [LPS-112983](https://issues.liferay.com/browse/LPS-112983)

#### What changed?

The `Liferay.BrowserSelectors.run()` function is no longer called on pages,
which as a result removes some CSS classes from the opening `<html>` tag. Many
of these are now added to the `<body>` element instead.

#### Who is affected?

This affects any code that relies on these CSS classes in the `<html>` element:

- `aol`
- `camino`
- `edgeHTML` or `edge`
- `firefox`
- `flock`
- `gecko`
- `icab`
- `ie`, `ie6`, `ie7`, `ie9`, or `ie11`
- `js`
- `konqueror`
- `mac`
- `mozilla`
- `netscape`
- `opera`
- `presto`
- `safari`
- `secure`
- `touch`
- `trident`
- `webkit`
- `win`

#### How should I update my code?

There's no direct replacement for the `Liferay.BrowserSelectors.run()` function,
but you can adapt your CSS and JavaScript to target new classes on the `<body>`
element instead. These classes are added to the `<body>` element to reflect
the browser you're currently using:

- `chrome`
- `edge`
- `firefox`
- `ie`
- `mobile`
- `other`

Alternatively, you can still invoke `Liferay.BrowserSelectors.run()` to apply
the old classes to the `<html>` element with the code below:

```
<aui:script use="liferay-browser-selectors">
	Liferay.BrowserSelectors.run();
</aui:script>
```

#### Why was this change made?

The classes, some of which referred to outdated browsers, were being added to
the top `<html>` element via legacy JavaScript that depended on Alloy UI. This
change, which removes the outdated browser references, is now done on the server
side, improving page loading times.

---------------------------------------

### Remove Support for Blocking Cache
- **Date:** 2020-Jun-17
- **JIRA Ticket:** [LPS-115687](https://issues.liferay.com/browse/LPS-115687)

#### What changed?

Blocking cache support was removed. These properties can no longer be used to
enable blocking cache:

- `ehcache.blocking.cache.allowed`
- `permissions.object.blocking.cache`
- `value.object.entity.blocking.cache`

#### Who is affected?

This affects anyone using the properties listed above.

#### How should I update my code?

There's no direct replacement for the removed feature. If you have code that
depends on it, you must implement it yourself.

#### Why was this change made?

This change was made to improve performance because blocking caches should never be enabled.

---------------------------------------

### Remove Support for Setting Cache Properties for Each Entity Model
- **Date:** 2020-Jun-24
- **JIRA Ticket:** [LPS-116049](https://issues.liferay.com/browse/LPS-116049)

#### What changed?

Support was removed for setting these cache properties for an entity:

- `value.object.entity.cache.enabled*`
- `value.object.finder.cache.enabled*`
- `value.object.column.bitmask.enabled*`

For example, these properties are for entity
`com.liferay.portal.kernel.model.User`:

- `value.object.entity.cache.enabled.com.liferay.portal.kernel.model.User`
- `value.object.finder.cache.enabled.com.liferay.portal.kernel.model.User`
- `value.object.column.bitmask.enabled.com.liferay.portal.kernel.model.User`

#### Who is affected?

This affects anyone using the properties listed above for an entity.

#### How should I update my code?

There's no direct replacement for the removed feature. You must remove
these properties from your entities.

#### Why was this change made?

This change was made because these properties are not useful for an entity.
---------------------------------------
