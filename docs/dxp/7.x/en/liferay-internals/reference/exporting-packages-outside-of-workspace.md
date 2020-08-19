# Exporting Packages Outside of Workspace

If you're [exporting packages](../fundamentals/exporting-packages.md) from a module JAR or plugin WAR project outside of [Workspace](../../developing-applications/tooling/liferay-workspace.md), make sure to add your `Export-Package` heading in the correct location.

| Project Type | `Export-Package` header location |
| :----------- | :------------------------------- |
| Module JAR (uses [Bnd](http://bnd.bndtools.org/)) | `[project]/bnd.bnd` |
| Module JAR (doesn't use [Bnd](http://bnd.bndtools.org/)) | `[module JAR]/META-INF/MANIFEST.MF` |
| Plugin WAR | `WEB-INF/liferay-plugin-package.properties` |

In module projects that don't use [Bnd](http://bnd.bndtools.org/), you must manually add package exports to an `Export-Package` header in the module JAR's `META-INF/MANIFEST.MF`.

In plugin WAR projects, you must add package exports to an `Export-Package` header in the project's `WEB-INF/liferay-plugin-package.properties`. On copying the WAR into the `[Liferay Home]/deploy` folder, the [WAB Generator](../../developing-applications/reference/deploying-wars-wab-generator.md) propagates the OSGi headers from the WAR's `liferay-plugin-package.properties` file to the `META-INF/MANIFEST.MF` file in the generated Web Application Bundle (WAB).

## Additional Information

* [Configuring Dependencies](./configuring-dependencies/configuring-dependencies.md)

* [Semantic Versioning](./semantic-versioning.md)

* [Deploying WARs \(WAB Generator\)](../../developing-applications/reference/deploying-wars-wab-generator.md)

* [Liferay Workspace](../../developing-applications/tooling/liferay-workspace.md)

* [Blade CLI](../../../developing-applications/tooling/blade-cli/generating-projects-with-blade-cli.md)

* [Liferay Developer Studio](../../developing-applications/tooling/developer-studio.md)