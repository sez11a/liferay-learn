# Liferay JS Generator Commands Reference


The available commands for bundles created with the Liferay JS Generator and migrated to use it are listed below. You can swap `npm` for `yarn` below if you prefer to use yarn instead.

| Command | Description |
| --- | --- |
| `npm run build` | Places the output of liferay-npm-bundler in the designated output folder. The standard output is a JAR file that can be deployed manually. |
| `npm run deploy` | Deploys the application to the configured server. This command is only available if you configured your liferay instance with the generator. |
| `npm run start` | Tests the application in a local webpack installation instead of a Portal server. This speeds up development because you can see live changes without the need to deploy. Note, however, that because it's outside a Liferay instance, you don't have access to Liferay's APIs. |
| `npm run translate` | Runs the translation features for your bundle. Note that this feature requires Microsoft Translator credentials. See [Using Translation Features in Your Widget](TODO:using-translation-features-in-your-widget) for more information. |

These commands are Available for [adapted apps](../developer-guide/adapting-apps-for-liferay.md):

| Command | Description |
| --- | --- |
| `npm run build:liferay` | Adds a deployable JAR to the `build.liferay` folder in your project. For example, if you want to build the JAR and copy it to another app server or Docker container, you can run this command. |
| `npm run deploy:liferay` | Adds a deployable JAR to the `build.liferay` folder in your project and deploys it to your Portal instance. |
| `npm run build` | Places the output of liferay-npm-bundler in the designated output folder. The standard output is a JAR file that can be deployed manually. |
| `npm run eject` | Clones the scripts to the project's directory and points `package.json` to them, removing the need for `react-scripts` dependencies. This is only applicable to adapted React projects. This lets you modify the build process to suit user needs, but effectively detaches the project from the React tools, preventing the developer from getting any further updates to the `react-scripts` logic. |
| `npm run start` | Tests the application in a local webpack installation instead of a Portal server. This speeds up development because you can see live changes without the need to deploy. Note, however, that because it's outside a Liferay instance, you don't have access to Liferay's APIs. |
| `npm run test` | Places the output of liferay-npm-bundler in the designated output folder. The standard output is a JAR file that can be deployed manually. |