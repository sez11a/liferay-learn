# Generating Projects with Blade CLI

Blade CLI exists to create, build, and deploy Liferay projects in Liferay Workspaces. Once created, these projects can be imported into an IDE or worked on directly. Here you'll learn the various ways in which you can create and manage Liferay projects.

## Creating a Liferay Workspace

Liferay Workspace is a set of folders on your machine where you store projects and DevOps configurations. See [Liferay Workspace](../../tooling/liferay-workspace.md) <!-- Placeholder until Workspace articles come through. --> for further information. To create a Liferay Workspace follow these steps,

1. In your CLI, navigate to the folder where you want to create a Liferay Workspace.

1. Run this command:

   ```bash
   blade init -v 7.3 [workspace name]
   ```

<!-- Let's ask the blade team to add some sort of success message to this command. Also - when I ran this to test (it was my first time) I thought that the brackets were required - but I ended up creating a workspace with brackets in the folder path. would it be clearer to say `blade init -v 7.3 your-workspace-name` ? -->

## Creating a Project

Projects exist in a Liferay Workspace. Here are the important options to remember:

**-t:** Specify the project template to use. You can get a list of these by typing `blade create -l`.

**-p:** Specify the package name for your class.

**-c:** Specify your class name.

**-v:** Specify the Liferay version; for example, `7.1`, `7.2`, or `7.3`.

Putting these together, if you want to create a Liferay MVC Portlet called "guestbook," use this command:

```bash
blade create -t mvc-portlet -p com.liferay.docs.portlet -c GuestbookPortlet -v 7.3 guestbook
```

This creates a project using the MVC Portlet template, a default package called `com.liferay.docs.portlet`, a portlet class called `GuestbookPortlet`, for Liferay 7.3, called *guestbook*. Liferay Workspaces can be imported into any IDE, because they are Gradle projects. The Liferay IntelliJ plugin or the Eclipse plugins in Liferay Developer Studio provide enhanced support.

## Creating Sample Projects

Liferay maintains a GitHub repository of [sample projects](https://github.com/liferay/liferay-blade-samples/tree/7.3). These are fully-implemented samples of various Liferay technologies you can use as a starting point for your projects. Rather than clone the repository to get access to them, however, you can create them locally using Blade CLI.

1. Find the sample project you want:

   ```bash
   blade samples
   ```

1. Say you want a working example of a [model listener](../../../liferay-internals/extending-liferay/creating-a-model-listener.md). Type this command:

   ```bash
   blade samples model-listener
   ```

1. If you want a specific version of the sample, you can pass in a version:

   ```bash
   blade samples -v 7.1 model-listener
   ```

## Converting Legacy Plugins SDK Projects

If you have Liferay projects prior to version 7.0, they are in a Plugins SDK. To use them with any version of Liferay beyond 6.2, you must migrate them from the Plugins SDK to a Liferay Workspace.

1. Create a [Liferay Workspace](#creating-a-liferay-workspace) if you haven't already.

1. From within the Liferay Workspace, execute this command:

   ```bash
   blade convert -s [path to old Plugins SDK] -a
   ```

   This converts all projects in the Plugins SDK to Workspace projects.

1. If you want to convert only a single project, use this command instead:

   ```bash
   blade convert -s [path to old Plugins SDK] [name of Plugins SDK project to convert]
   ```

- When converting a project containing Service Builder services, Blade CLI creates separate API and service OSGi modules. The portlet remains a WAR and moves into the `wars` folder.
- Themes are converted to leverage NodeJS like Liferay 7.x themes. To convert a Java-based theme, add the `-t` option, which uses the Theme Builder Gradle plugin instead.

## Related Topics

[Liferay Workspace](../../tooling/liferay-workspace.md) <!-- Placeholder until Workspace articles come through. -->
