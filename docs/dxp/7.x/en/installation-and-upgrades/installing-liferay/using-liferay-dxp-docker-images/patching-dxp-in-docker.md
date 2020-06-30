# Patching DXP in Docker

Liferay provides [patches](../../maintaining-a-liferay-dxp-installation/patching-liferay/patching-liferay.md) that fix DXP issues and a Patching Tool for applying the patches. [Fix Packs](../../maintaining-a-liferay-dxp-installation/patching-liferay/patching-liferay.md#fix-packs), [Security Fix Packs](../../maintaining-a-liferay-dxp-installation/patching-liferay/patching-liferay.md#scurity-fix-packs), and [Service Packs](../../maintaining-a-liferay-dxp-installation/patching-liferay/patching-liferay.md#service-packs) are provided as new DXP images (on [Docker Hub](https://hub.docker.com/r/liferay/dxp)) that you migrate to. Liferay also provides [Security Fix Packs](../../maintaining-a-liferay-dxp-installation/patching-liferay/patching-liferay.md#scurity-fix-packs), [Hotfixes](../../maintaining-a-liferay-dxp-installation/patching-liferay/patching-liferay.md#hotfixes), and new [Patching Tool](../../maintaining-a-liferay-dxp-installation/patching-liferay/installing-the-patching-tool.md) versions to install to your containers.

| Enterprise subscription

Here are the DXP container patching topics:

* [Using Fix Pack, Security Fix Pack, and Service Pack Images](#using-fix-pack-security-fix-pack-and-service-pack-images)
* [Installing a Patch](#installing-a-patch)
* [Reverting a Patch](#reverting-a-patch)
* [Updating the Patching Tool](#updating-the-patching-tool)
* [Upgrading the Database for a Patch](#upgrading-the-database-for-a-patch)

## Using Fix Pack, Security Fix Pack, and Service Pack Images

Fix Pack, Security Fix Pack, and Service Pack images are based on [Slim Bundles](../..//maintaining-a-liferay-dxp-installation/patching-liferay/advanced-patching/using-slim-bundles.md). They start up faster and have a smaller footprint than regular [Liferay Tomcat bundles](../installing-a-liferay-tomcat-bundle.md). They are limited, however, to one additional patch installation, such as a Hotfix or a Security Fix Pack (on top of a Fix Pack or Service Pack).

Using a new Fix Pack, Security Fix Pack, or Service Pack requires migrating to a container based on that image. Here are steps for creating such a container.

1. Stop your current DXP container.

1. Back up your container's artifacts and configuration files using a source control repository or some other means.

    ```bash
    git commit -a
    ```

1. Download the new image from [Docker Hub](https://hub.docker.com/r/liferay/dxp).

    ```bash
    docker pull liferay/dxp:[tag]
    ```

1. Create a new container that is based on the image, using qualified volumes or bind mounts to provide your artifacts and configuration files to the container.

    For example, if your files are in subfolders of a host folder like this ...

    ```
    [host folder]
    ├───deploy
    ├───files
    ├───patching
    └───scripts
    ```

    then you can [bind mount the host folder to the container's `/mnt/liferay` folder](./providing-files-to-the-container.md#bind-mounting-a-host-folder-to-mnt-liferay).

    ```bash
    docker run ... -v [host folder path]:/mnt/liferay liferay/dxp:[tag]
    ```

DXP launches in a container based on the new image.

## Installing a Patch

A single patch can be applied to a Fix Pack image or Service Pack image. The patch can be a Hotfix, Security Fix Pack, or a patch that includes both kinds of fixes. To get this last type of patch, create a [Help Center ticket](https://help.liferay.com/hc) and request a patch that includes the latest security fixes and fixes to product issues on top of your current Fix Pack level.

Patching requirements:

1. No existing patches in the container. Any existing patch must be [reverted](#reverting-a-patch) before applying a new patch.

1. The image must not be Security Fix Pack image; such an image already includes a patch (the Security Fix Pack).

1. A folder in your volume or bind mount that maps to the container's `/mnt/liferay/patching` folder. Please see [Providing Files to the Container](./providing-files-to-the-container.md) for more information.

Here are two methods for installing patches to containers:

1. [Installing to an existing container](#installing-to-an-existing-container).

1. [Installing to a new container](#installing-to-a-new-container).

### Installing to an Existing Container

Here are the steps for installing a patch to your existing container:

1. [Stop your current container](./dxp-docker-container-basics.md#stopping-a-container).

1. [Download](https://customer.liferay.com/downloads) the patch and copy it to a folder in your volume or [bind mount](./providing-files-to-the-container.md) that maps to the container's `/mnt/liferay/patching` folder.

1. [Restart your container](./dxp-docker-container-basics.md#restartings-a-container).

### Installing to a New Container

Here are steps for installing a patch to a new container:

1. Create a host folder and a subfolder called `patching`.

    ```bash
    mkdir -p [host folder]/patching
    ```

1. [Download](https://customer.liferay.com/downloads) the patch and copy it to your `[host folder]/patching` folder. For example,

   ```bash
   cp ~/[patch file] [host folder]/patching
   ```

1. Stop your current DXP container, if it's running.

1. Create a container, that includes a bind mount that maps the patch file's folder to the container's `/mnt/liferay/patching` folder. Since this example's patch file is in a folder called `patching`, you can [bind mount to the container's `/mnt/liferay` folder](./providing-files-to-the-container.md#bind-mounting-a-host-folder-to-mnt-liferay).

    ```bash
    docker run ... -v [host folder path]:/mnt/liferay liferay/dxp:[tag]
    ```

    ```note::
       Please see `Providing Files to the Container <./providing-files-to-the-container.md>`_ for more information on specifying bind mounts.
    ```

The Patching Tool installs the patch and DXP launches.

```important::
   If the Patching Tool reports this message: ``[patch file] is incompatible with Patching Tool version [x.y.z]``, install the latest Patching Tool. See `Installing the Patching Tool <#installing-the-patching-tool>`_ for details.
```

## Reverting a Patch

If you want to revert a patch from a container or install a different patch to the container, you must remove the container and create a new one.

1. [Stop the container](./dxp-docker-container-basics.md#stopping-a-container).

    ```bash
    docker stop [container]
    ```

1. Remove the container.

    ```bash
    docker rm [container]
    ```

1. Use the `docker run` arguments you used previously to create a new container from the same image or an image that has a compatible Fix Pack Level. Apply any patches you want via a volume or [bind mount](./providing-files-to-the-container.md#bind-mounting-a-host-folder-to-mnt-liferay).

DXP is running on the Fix Pack Level you have specified, with the old patch gone and optionally replaced.

## Updating the Patching Tool

If your current Patching Tool is incompatible with the patch you're installing, the Patching Tool reports this message: `[patch file] is incompatible with Patching Tool version [x.y.z]`.

Here are steps to install a new [Patching Tool](../../maintaining-a-liferay-dxp-installation/patching-liferay/installing-the-patching-tool.md) version:

1. Download the latest Patching Tool from the [Customer Portal](https://customer.liferay.com/downloads?p_p_id=com_liferay_osb_customer_downloads_display_web_DownloadsDisplayPortlet&_com_liferay_osb_customer_downloads_display_web_DownloadsDisplayPortlet_productAssetCategoryId=118191019&_com_liferay_osb_customer_downloads_display_web_DownloadsDisplayPortlet_fileTypeAssetCategoryId=118191066).

1. If the Patching Tool ZIP file name doesn't have this format `patching-tool-x.y.z.zip`, where `x.y.z` are the tool's major, minor, and micro version number, rename it to use that format. For example,

    ```bash
    mv patching-tool.zip patching-tool-2.0.15.zip
    ```

1. Install the Patching Tool ZIP file to the container via the container's `/mnt/liferay/patching` folder, in the same way that [Hotfixes and Security Fix Packs are installed](#installing-a-hotfix-or-security-fix-pack).

On restarting your container or running a new container, the container entry point installs the new Patching Tool.

## Upgrading the Database for a Patch

If a patch requires upgrading the database, you must upgrade it using the Database Upgrade Tool in a non-containerized environment, such as a [Liferay Tomcat Bundle installation](../installing-a-liferay-tomcat-bundle.md) or [Liferay on an Application Server](https://learn.liferay.com/dxp/7.x/en/installation-and-upgrades/installing-liferay/installing_liferay_on_an_application_server.html). Please see [Using the Database Upgrade Tool](../../upgrading-liferay-dxp/upgrade-basics/using-the-database-upgrade-tool.md) for more information.

After the database is upgraded, run your container that uses that database.

## Additional Information

* [Installing Apps and Other Artifacts to Containers](./installing-apps-and-other-artifacts-to-containers.md)
* [DXP Docker Container Basics](./dxp-docker-container-basics.md)
* [Providing Files to the Container](./providing-files-to-the-container.md)
* [DXP Container Lifecycle and API](./dxp-container-lifecycle-and-api.md)
* [Using the Database Upgrade Tool](../../upgrading-liferay-dxp/upgrade-basics/using-the-database-upgrade-tool.md)
