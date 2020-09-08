# Fundamentals

Liferay development projects consist primarily of simple .jar files. These contain a few extra configuration files that make them OSGi modules, but they're easily understandable by anyone who knows Java.

Part of the power of OSGi is that you have a single, consistent way to build projects that access DXP's API, extend and override various features, or package new software for deployment. These projects, or modules, form an ecosystem where they can depend on each other for functionality and be manipulated at runtime.

These are the fundamentals of Liferay DXP's ecosystem.

* **[Module Projects](./module-projects.md)** explains what an OSGi module is, its project structure, and how it is used by Liferay's OSGi framework.

* **[Configuring Dependencies](./configuring-dependencies/configuring-dependencies.md)** demonstrates how to identify and configure Liferay artifacts and third-party artifacts to use their Java packages in your projects.

* **[Importing](./importing-packages.md) and [Exporting Packages](./exporting-packages.md)** shows how to use functionality from other modules and publish your functionality for other modules to use. Liferay's tooling detects package use and specifies package imports automatically.

* **[Semantic Versioning](./semantic-versioning.md)** describes how Liferay manages dependencies on code as it evolves to new versions, so that you can use the same mechanism to manage your version and avoid code breakage.

* **[Gogo Shell](./using-the-gogo-shell/using-the-gogo-shell.md)** shows how you can examine components, debug issues, and manage deployments at runtime by issuing commands into a shell.

You can begin by learning about [module projects](./module-projects.md).
