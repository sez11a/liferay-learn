# Upgrading Your DXP Cloud Stack

Upgrading your repository to use the new structure in DXP Cloud version 4 allows you to take advantage of Liferay Workspace within your repository, keep your services up to date, and use a range of new and updated features.

Upgrading to the new repository structure involves the use of an [upgrade script](https://github.com/LiferayCloud/stack-upgrade/archive/release.zip).

## Preparing to Upgrade

In order to upgrade to version 4.x.x of the DXP Cloud stack, your current services must be at 3.x.x. To verify your current version, locate the gradle.properties file in your repository. Before upgrading to version 4.x.x, epositories follow this structure:

```
repository
├── gradle
├── lcp
├── liferay
├── modules
├── themes
├── build.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
├── Jenkinsfile
├── README.md
├── README-dxpcloud.md
└── settings.gradle
```

```important::
   If the repository does not follow this structure (and there is no ``gradle.properties`` file or ``lcp`` folder present at the root), then this indicates an upgrade to version 4.x.x has already been completed.
```

Open the `gradle.properties` file and review the versions specified for any of your Docker images. For example, this is the image version for the Liferay service:

```properties
liferay.workspace.lcp.liferay.image=liferaycloud/liferay-dxp:7.2.10-sp1-fp4-3.0.19
```

The number at the end of the Docker image name indicates the version of the DXP Cloud stack you are using. Ensure that the image name ends with `3.x.x` (in this example, liferay-dxp:7.2.10-sp1-fp4-**3.0.19**).

Next, make sure your Git repository is on a clean branch. The upgrade script will commit any previously uncommitted files and check out a new branch called `upgrade-workspace`.

```important::
   If you already have an ``upgrade-workspace`` branch, then the script will skip creating it and will work with your current branch.
```

## Running the Upgrade Script

Perform the following steps to upgrade:

1. Download the [upgrade script](https://github.com/LiferayCloud/stack-upgrade/archive/release.zip), and extract it into the root of your local DXP Cloud repository:

    ```bash
    curl -L https://github.com/LiferayCloud/stack-upgrade/archive/release.zip -O
    ```

    ```bash
    unzip -j release.zip stack-upgrade-release/upgrade-workspace.sh -d path/to/project/folder
    ```

1. Run the upgrade script:

    ```bash
    cd /path/to/project/folder
    ```

    ```bash
    ./upgrade-workspace.sh
    ```

1. Read the script's starting output, and enter your project's ID at the prompt:

    ```
    Press enter to continue:
    Please enter your project id: lfrlearn
    ```

1. At the prompt, authenticate into the DXP Cloud Console.

1. Enter the number corresponding to the Liferay version to use for your services:

    ```
    1) 7.0
    2) 7.1
    3) 7.2
    Please select the Liferay DXP version, which will determine the Liferay CLOUD image set in liferay/LCP.json and the Liferay image set in liferay/gradle.properties: 3
    ```

1. At the prompt, enter a comma-delimited list of the environments you use for your project files (not including `common`):

    ```
    Please enter a comma-delimited list of environments: dev,uat,prd
    ```

    ```important::
       Only enter the names of Liferay environments that you add files to in your project repository. Do not add the infrastructure (`infra`) environment.
    ```

1. If you want to install Elasticsearch plugins to all of your Liferay environments, enter a comma-delimited list of their names at the prompt. Otherwise, press enter.

    ```
    Please enter a comma-delimited list of elasticsearch plugins:
    ```

    The script creates and checks out a new branch in your repository, with the upgrade script added to your `.gitignore` file.

1. Verify that the script execution was successful by checking the new organization of your repository, with the service folders now on the root level:

    ```
    repository
    ├── backup
    ├── build
    ├── ci
    ├── database
    ├── liferay
    ├── search
    ├── webserver
    └── upgrade-workspace.sh
    ```

Your repository is now reorganized into a structure where the `liferay` folder is a Liferay Workspace, and your services are upgraded to 4.x.x.

## Verify CI Service's Environment Variables

The environment variables within your project's `ci/LCP.json` file may now behave differently after you have completed the service stack upgrade. Before continuing with your upgraded project, make sure that your `ci` service's environment variables reflect the correct configuration.

Verify that the following properties are set up correctly for your project's integration with version control (in this example, using GitHub):

```json
{
    "LCP_CI_SCM_PROVIDER": "github",
    "LCP_CI_SCM_REPOSITORY_OWNER": "OWNER_NAME",
    "LCP_CI_SCM_REPOSITORY_NAME": "PROJECT_NAME",
    "LCP_CI_SCM_TOKEN": "AUTH_TOKEN",
}
```

Since a default Jenkinsfile is [no longer required](./dxp-cloud-project-changes-in-version-4.md#ci-service-changes) in your project, the Jenkinsfile at the root of your project may also be removed after the upgrade. If you intend to use the default Jenkinsfile for your project, ensure that the `ci` services environment variables reflect this:

```json
{
    "LCP_CI_USE_DEFAULT_JENKINSFILE": "true",
    "LCP_CI_SCM_JENKINSFILE_HOOKS_DIR": "ci/",
}
```

```note::
   You must define the ``LCP_CI_SCM_JENKINSFILE_HOOKS_DIR`` variable if you want to use hooks to extend the default Jenkinsfile.
```

If you are defining your own Jenkinsfile within your `ci` service directory to override the default, then ensure your environment variables instead look like this:

```json
{
    "LCP_CI_USE_DEFAULT_JENKINSFILE": "false",
    "LCP_CI_SCM_JENKINSFILE_PATH": "ci/Jenkinsfile",
}
```

## Next Steps

After your local repository has changed, use the [CLI tool](./command-line-tool.md)'s `lcp deploy` command to deploy your `ci` service to the `infra` environment. This ensures that the changes to the `ci` service are deployed first, which will allow further changes to deploy correctly.

You may then want to [deploy the new service versions](../build-and-deploy/walking-through-the-deployment-life-cycle.md) to a development environment, or explore some of the new functionality in the new versions of your DXP Cloud services. See the [explanation of the changes](./dxp-cloud-project-changes-in-version-4.md) to learn more.

## Additional Information

* [Understanding Service Stack Versions](./understanding-service-stack-versions.md)