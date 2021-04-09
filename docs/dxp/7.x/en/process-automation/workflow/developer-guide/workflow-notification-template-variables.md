# Workflow Notification Template Variables

> Availability: Liferay CE/DXP 7.3+

Some variables are auto-injected into the workflow notification context. These are handy to use in your workflow notification templates.

Normally in Freemarker, you must declare variables explicitly:

```markup
<#assign variableName = "Variable Name" />
```

Injected variables are already declared and can be used directly in the template.

```markup
${variableName}
```

To use these variables, you must know which are available in your workflow notification context. The variables available change depending on the workflow definition details and the asset in the workflow. The method presented here shows you how to get the list of variables for your specific context, so you don't have to do any guesswork.

## Discovering the Workflow Notification Template Variables

Enable DEBUG level [logging]( ./../../../system-administration/using-the-server-administration-panel/configuring-logging.md) for the `TemplateNotificationMessageGenerator` class to display the variables available in your workflow notification context:

1. Go to Control Panel &rarr; Configuration &rarr; Server Administration.
 
1. Click the Log Levels tab.
 
1. Add a Log Level with this configuration:

   - *Logger Name*: `com.liferay.portal.workflow.kaleo.runtime.internal.notification.TemplateNotificationMessageGenerator`
   - *Log Level*: `DEBUG`

1. [Activate a workflow definition]( ./../using-workflows/activating-workflow.md) (e.g., the Single Approver definition) on an asset (e.g., Blogs Entry).

1. Submit a test entry and log messages appear that reveal the workflow available notification template variables in the context of your workflow. 

For example, the Single Approver workflow shows these variables on initial submission of an asset:

```bash
2020-03-30 14:21:42.089 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] saxReaderUtil (class com.sun.proxy.$Proxy447)
2020-03-30 14:21:42.097 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] getterUtil (class com.liferay.portal.kernel.util.GetterUtil_IW)
2020-03-30 14:21:42.098 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] portalPermission (class com.liferay.portal.service.permission.PortalPermissionImpl)
2020-03-30 14:21:42.098 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] entryClassPK (class java.lang.String)
2020-03-30 14:21:42.098 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] layoutPermission (class com.liferay.portal.service.permission.LayoutPermissionImpl)
2020-03-30 14:21:42.098 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] expandoTableLocalService (class com.sun.proxy.$Proxy43)
2020-03-30 14:21:42.098 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] localeUtil (class com.liferay.portal.kernel.util.LocaleUtil)
2020-03-30 14:21:42.099 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] groupId (class java.lang.String)
2020-03-30 14:21:42.099 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] portalUtil (class com.liferay.portal.util.PortalImpl)
...
```

The snippet of output above shows that `saxReaderUtil`, `getterUtil`, `portalPermission`, `entryClassPK`, `layoutPermission`, `expandoTableLocalService`, `localeUtil`, `groupId`, and `portalUtil` are available in the context that caused these messages to be printed in the log.

### Using the Workflow Notification Template Variables

The contextually injected notification variables can be categorized into two main types:

1. **Value** variables provide a single value. If a variable provides a single value, you can use it to display that value in the notification, or you can pass it as a parameter to an operation that retrieves some other useful information to be displayed in the notification message.

   _Example:_ The Single Approver definition provides this notification in a FreeMarker template:

   ```markup
   ${userName} sent you a ${entryType} for review in the workflow.
   ```

   The `userName` and `entryType` provide values, so the notification might render like this:

   _Joe Bloggs sent you a Blogs Entry for review in the workflow._


   The value type variables can also be checked for content. The Single Approver definition also contains this notification template:

   ```markup
   Your submission was reviewed<#if taskComments?has_content> and the reviewer applied the following ${taskComments}</#if>.
   ```

   If the reviewer provided task comments, they're shown. Otherwise, the notification recipient only sees

   _Your submission was reviewed_

1. **Operation** variables expose a Liferay DXP Java class, so you can access its operations in the notification template. For these variables, you must familiarize yourself with the class's [Javadoc](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/) or look into the [source code](https://github.com/liferay/liferay-portal/tree/[$LIFERAY_LEARN_PORTAL_GIT_TAG$]) to understand its operations. 

   _Example:_ This FreeMarker gets you the current date, using the default locale, in a specific pattern (_Month/Day/Year, Hour:Minute_): 

   ```markup
   ${dateUtil.getCurrentDate("MM/dd/yyyy, HH:mm",  localeUtil.getDefault())}`
   ```

## Understanding the Workflow Notification Template Variables

These are the variables logged when you enable the Single Approver Definition
for Blogs Entries, then submit a new entry for publication:

| Variable Name                | Value    | Operation | Description or Link                       |
| ---------------------------- | -------- | --------- |--------------------------------------------- |
| ${saxReaderUtil}             |          | &#10004;  | [SaxReaderUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/xml/SAXReaderUtil.html) |
| ${getterUtil}                |          | &#10004;  | [GetterUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/GetterUtil.html) |
| ${portalPermission}          |          | &#10004;  | [PortalPermission](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/PortalPermission.html) |
| ${entryClassPK}              | &#10004; |           | The primary key of the Entry Class           |
| ${layoutPermission}          |          | &#10004;  | [LayoutPermission](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/LayoutPermission.html) |
| ${expandoTableLocalService}  |          | &#10004;  | [ExpandoTableLocalService](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/expando/kernel/service/ExpandoTableLocalService.html) |
| ${localeUtil}                |          | &#10004;  | [LocaleUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/LocaleUtil.html) |
| ${groupId}                   | &#10004; |           | The ID of the group                          |
| ${portalUtil}                |          | &#10004;  | [PortalUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/PortalUtil.html) |
| ${validator}                 |          | &#10004;  | [Validator](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/Validator.html) |
| ${dateUtil}                  |          | &#10004;  | [DateUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/DateUtil.html) |
| ${serviceLocator}            |          | &#10004;  | [ServiceLocator](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-impl/com/liferay/portal/template/ServiceLocator.html) |
| ${serviceContext}            |          | &#10004;  | [ServiceContext](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/ServiceContext.html) |
| ${jsonFactoryUtil}           |          | &#10004;  | [JSONFactoryUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/json/JSONFactoryUtil.html) |
| ${stringUtil}                |          | &#10004;  | [StringUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/StringUtil.html) |
| ${freeMarkerPortletPreferences} |       | &#10004;  | [TemplatePortletPreferences](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-impl/com/liferay/portal/template/TemplatePortletPreferences.html) |
| ${dateFormats}              |           | &#10004;  | [FastDateFormatFactory](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/FastDateFormatFactory.html) |
| ${dateFormatFactory}         |          | &#10004;  | [DateFormatFactory](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/DateFormatFactory.html) |
| ${userPortraitURL}           | &#10004; |           |  Applies only to Blogs Entries; returns the URL to the User's portrait.|
| ${portal}                    |          | &#10004;  | [Portal](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/Portal.html) |
| ${commonPermission}          |          | &#10004;  | [CommonPermission](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/CommonPermission.html) |
| ${userURL}                   | &#10004; |           | Applies only to Blogs Entries; returns the URL to a logged in User's profile page. See [](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-impl/com/liferay/portal/model/impl/UserImpl.html#getDisplayURL). |
| ${expandoValueLocalService}  |          | &#10004;  | [ExpandoValueLocalService](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/expando/kernel/service/ExpandoValueLocalService.html) |
| ${entryType}                 | &#10004; |           | The type of entry in the workflow (e.g., Blogs Entry) |
| ${organizationPermission}    |          | &#10004;  | [OrganizationPermission](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/OrganizationPermission.html) |
| ${passwordPolicyPermission}  |          | &#10004;  | [PasswordPolicyPermission](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/PasswordPolicyPermission.html) |
| ${expandoColumnLocalService} |          | &#10004;  | [ExpandoColumnLocalService](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/expando/kernel/service/ExpandoColumnLocalService.html) |
| ${taskComments}              | &#10004; |           | The comments, if any, left by the reviewer |
| ${staticFieldGetter}         |          | &#10004;  | [StaticFieldGetter](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/StaticFieldGetter.html) |
| ${htmlUtil}                  |          | &#10004;  | [HtmlUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/HtmlUtil.html) |
| ${languageUtil}              |          | &#10004;  | [LanguageUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/language/LanguageUtil.html) |
| ${enumUtil}                  |          | &#10004;  | Returns the result of `beansWrapper.getEnumModels()`; for [Accessing Enums](https://freemarker.apache.org/docs/pgui_misc_beanwrapper.html#jdk_15_enums) |
| ${windowStateFactory}        |          | &#10004;  | [WindowStateFactory](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/portlet/WindowStateFactory.html) |
| ${companyId}                 | &#10004; |           | The ID of the portal's Company/Virtual Instance |
| ${unicodeFormatter}          |          | &#10004;  | [UnicodeFormatter](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/UnicodeFormatter.html) |
| ${propsUtil}                 |          | &#10004;  | [PropsUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/PropsUtil.html) |
| ${browserSniffer}            |          | &#10004;  | [BrowserSniffer](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/servlet/BrowserSniffer.html) |
| ${portletProviderAction}     | &#10004; |           | Returns the outcome of the [PortletProvider#Action](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/portlet/PortletProvider.Action.html) call |
| ${taskName}                  | &#10004; |           | The name of the task the notification is in |
| ${httpUtil}                  |          | &#10004;  | [HttpUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/HttpUtil.html) |
| ${portletURLFactory}         |          | &#10004;  | [PortletURLFactory](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/portlet/PortletURLFactory.html) |
| ${imageToken}                | &#10004; |           | An image token from [WebServerServletToken.getToken(long imageId)](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/webserver/WebServerServletToken.html); is deprecated and will be removed |
| ${groupPermission}           |          | &#10004;  | [GroupPermission](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/GroupPermission.html) |
| ${timeZoneUtil}              |          | &#10004;  | [TimeZoneUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/TimeZoneUtil.html) |
| ${unicodeLanguageUtil}       |          | &#10004;  | [UnicodeLanguageUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/language/UnicodeLanguageUtil.html) |
| ${expandoRowLocalService}    |          | &#10004;  | [ExpandoRowLocalService](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/expando/kernel/service/ExpandoRowLocalService.html) |
| ${auditRouterUtil}           |          | &#10004;  | [AuditRouterUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/audit/AuditRouterUtil.html) |
| ${kaleoTaskInstanceToken}    |          | &#10004;  | [See the documentation on workflow scripting](./../developer-guide/using-the-script-engine-in-workflow.md#predefined-variables) |
| ${accountPermission}         |          | &#10004;  | [AccountPermission](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/AccountPermission.html) |
| ${httpUtilUnsafe}            |          | &#10004;  | Like `${httpUtil}`, an instance of [Http](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/Http.html), but can be used to access the local network |
| ${workflowTaskAssignees}     | &#10004; |          | [See the documentation on workflow scripting](./../developer-guide/using-the-script-engine-in-workflow.md#predefined-variables) |
| ${random}                   |           | &#10004;  | A Java utility for generating [Random](https://docs.oracle.com/javase/8/docs/api/java/util/Random.html) numbers; is deprecated and will be removed in the future |
| ${rolePermission}           |           | &#10004;  | [RolePermission](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/RolePermission.html) |
| ${portletPermission}        |           | &#10004;  | [PortletPermission](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/PortletPermission.html) |
| ${paramUtil}                |           | &#10004;  | [ParamUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/ParamUtil.html) |
| ${locationPermission}       | &#10004;  |           | Returns the outcome of `OrganizationPermissionUtil.getOrganizationPermission()` |
| ${calendarFactory}          |           | &#10004;  | [CalendarFactory](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/CalendarFactory.html) |
| ${webServerToken}           | &#10004; |           | An image token from [WebServerServletToken.getToken(long imageId)](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/webserver/WebServerServletToken.html) |
| ${sessionClicks}            |           | &#10004;  | [SessionClicks](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/SessionClicks.html) |
| ${userPermission}           |           | &#10004;  | [UserPermission](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/UserPermission.html) |
| ${entryClassName}           | &#10004;  |
| ${userGroupPermission}      |           | &#10004;  | [UserGroupPermission](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/UserGroupPermission.html) |
| ${arrayUtil}                |           | &#10004;  | [ArrayUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/ArrayUtil.html) |
| ${userName}                 | &#10004;  |           | The User Name of the last user to intervene in the workflow |
| ${userId}                   | &#10004;  |           | The User ID of the last user to intervene in the workflow. [See the Workflow Scripting article to understand the logic.](./../developer-guide/using-the-script-engine-in-workflow.md#predefined-variables)
| ${prefsPropsUtil}           |           | &#10004;  | [PrefsPropsUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/PrefsPropsUtil.html) |
| ${url}                      | &#10004;  |           | a URL for certain content types that are displayed on a page (e.g., Blogs Entries, Message Boards Messages, and Wiki Pages
| ${kaleoInstanceToken}       | &#10004;  |           | [See the Workflow Scripting article.](./../developer-guide/using-the-script-engine-in-workflow.md#predefined-variables)
| ${utilLocator}              |           | &#10004;  | [UtilLocator](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-impl/com/liferay/portal/template/UtilLocator.html) |
| ${objectUtil}               |           | &#10004;  | Returns a `new LiferayObjectConstructor()` |
| ${urlCodec}                 |           | &#10004;  | [URLCodec](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/URLCodec.html) |
| ${portletModeFactory}       |           | &#10004;  | [PortletModeFactory](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/portlet/PortletModeFactory.html) |
| ${imageToolUtil}            |           | &#10004;  | [ImageToolUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/image/ImageToolUtil.html) |
| ${auditMessageFactoryUtil}  |           | &#10004;  | [AuditMessageFactoryUtil](https://docs.liferay.com/ce/portal/7.3-latest/javadocs/portal-kernel/com/liferay/portal/kernel/audit/AuditMessageFactoryUtil.html) |
| ${staticUtil}               |           | &#10004;  | Returns the result of `beansWrapper.getStaticModels()`; for [Accessing static methods](https://freemarker.apache.org/docs/pgui_misc_beanwrapper.html#autoid_60) |
