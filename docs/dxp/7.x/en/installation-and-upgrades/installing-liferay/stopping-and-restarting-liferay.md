# Stopping and Restarting Liferay

The best ways to stop and restart Liferay depend on the application server you're using and whether you're running in a production environment.

## Development Server

In development, use whatever is most convenient. Here are some examples of stopping and restarting in development.

| Development Environment | Stopping | Restarting |
| :---------------------- | :------- | :--------- |
| Liferay Workspace | `blade server stop` | `blade server start` |
| Docker image | `Ctrl + C` | `docker start [container]` |
| Tomcat bundle | `tomat-version/bin/startup.sh` | `tomcat-server/bin/shutdown.sh` or `Ctrl + C` |
| Any app server | See vendor documentation | See vendor documentation or `Ctrl + C` |

## Production Server

If you're running in a production or a mission critical environment, see the application server vendor documentation for stopping and restarting the application server.

## Additional Information

* [Running a Liferay Tomcat Bundle For the First Time](./running-liferay-for-the-first-time)
* [Liferay Docker Container Basics](./using-liferay-dxp-docker-images/dxp-docker-container-basics.md)
* [Liferay Workspace](../../developing-applications/tooling/liferay-workspace.md)