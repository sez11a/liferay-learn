# Introduction to REST Builder

Creating new API endpoints for your applications may be essential to effectively integrate with Liferay DXP. REST Builder is a tool that can help you generate these APIs quickly and effectively. It uses the [OpenAPI Specification](https://www.openapis.org/) to generate REST and [GraphQL](https://graphql.org/) APIs.

## What is REST Builder?

REST Builder is a code generation tool that uses the configuration you define in a `rest-config.yaml` and `rest-openapi.yaml` file to generate most of the code necessary for your API to work all at once. You configure the names and folders to generate the code in, and REST Builder generates all of the necessary files. The only thing you need to add from there is your implementation logic.

REST Builder is run using the Gradle task `buildREST` from your `impl` module in a Liferay workspace. Run this command to generate all of the scaffolding code, interfaces, and even the resource classes for you to add your implementation logic to.

In general, you can use the `@generated` annotation above the class name in each Java file to distinguish whether it is a file you should add your own logic to, or if it will be re-generated the next time REST Builder runs. If the annotation is not present, then the file is meant for you to add your own code to and will not be overwritten.

## Generated Code Structure

REST Builder generates several packages of code for you, so that you can focus on just your implementation class. However, you must start by creating the locations for each of your modules. In a conventional Liferay workspace, you only need to create the folders for each module (each with their own `bnd.bnd` and `build.gradle` files).

After you create the initial configuration for your modules,  and the [REST Builder configuration files](#adding-your-api-configurations) (`rest-config.yaml` and `rest-openapi.yaml`), then running REST Builder will generate the following structure:

```
api
- bnd.bnd
- build.gradle
- <apiDir>
---- <apiPackagePath>
----- dto
------ <version>
------- Schema object classes
----- resource
------ <version>
------- Schema object resource interfaces
```

The API module contains the Java classes for any custom objects you have defined (as [object schemas](#defining-schemas) in ``rest-openapi.yaml``) within the `dto` package. Reference these classes to work with your custom objects when you are adding your implementation logic.

```note::
   The ``apiPackagePath`` and ``apiDir`` are both defined in your project's ``rest-config.yaml`` file (the part of the ``apiDir`` that specifies the location of the ``api`` folder itself is not included in the structure it adds). The ``version`` path is configured in the ``rest-openapi.yaml`` file.
```

REST Builder generates the following structure for `impl` modules:

```
impl
- bnd.bnd
- build.gradle
- rest-config.yaml
- rest-openapi.yaml
- src
-- main
-- java
--- <apiPackagePath>
---- internal
----- graphql
------ GraphQL endpoint code
----- jaxrs
------ application
------- JAX-RS application code
----- resource
------ <version>
------- *ResourceImpl classes
--- resources
---- OSGI-INF
----- liferay
------ rest
------- <version>
-------- Generated properties files
```

GraphQL endpoint code and JAX-RS application code are both generated in the `graphql` and `jaxrs` packages, respectively. Your own API implementation is added into the appropriate `*ResourceImpl` class within the `resource` package.

### Configuring Client and Test Modules

You can also configure a `client` and `test` module for REST Builder to generate client and test code, respectively. Define a `clientDir` or `testDir` path in your `rest-config.yaml` for it to also generate more code within these modules.

As with the `api` and `impl` modules, REST Builder uses the directory paths, `apiPackagePath` values, and `version` value to define the folders that it will add classes into.

For `test` modules, you can define the implementation of your tests to the appropriate `*ResourceTest` Java class.

## Adding Your API Configurations

The details of the API you intend to add are configured in your project's `rest-openapi.yaml`. There are three main sections of this file that you can define: an `info` section, a `components` section, and a `paths` section.

### Defining Basic API Information

The `info` section of your `rest-openapi.yaml` file is the most straightforward section. This section defines the basic information for your APIs that will be used by the JAX-RS application when it becomes available, as well as the OpenAPI version beneath it.

Here is an example of an `info` section: 

```
info:
    description:
        "This is an example API."
    license:
        name: "Apache 2.0"
        url: "http://www.apache.org/licenses/LICENSE-2.0.html"
    title: "Journal Article API Expansion"
    version: v1.0
openapi: 3.0.1
```

### Defining Schemas

Schemas are the main mechanism REST Builder provides for defining new data types for your APIs. If you need REST Builder to create a new entity or data structure that you can use as a return type or parameter for your APIs, then you must define it as an object with its own schema in the `components` section of your `rest-openapi.yaml` file.

Objects are each defined with a name, a `type` (usually `object`, unless it only represents a basic type), and then a list of `properties` (their fields in the Java code) that they contain. Each of the properties also has a `type`, as well as a `description` that will be used for generated Java documentation.

Here is an example of a `components` section that defines one new schema (for a "BasicUser", with only a name and ID):

```
components:
    schemas:
        BasicUser:
            description:
                A basic user with only a name and ID.
            properties:
                name:
                    description:
                        The user's name as a string.
                    type:
                        string
                id:
                    description:
                        The user's ID as an integer.
                    type:
                        integer
            type:
                object
```

See the [OpenAPI specification](https://swagger.io/docs/specification/data-models/data-types/) for a list of all the supported basic data types that you can use for schemas.

```tip::
   If your API only needs to return one of the basic data types, then you may not need to define any schemas in your ``rest-openapi.yaml`` file.
```

### Defining the APIs

The last section, `paths`, specifies the details of the APIs themselves. Each API must be defined within its own path, which is added to the end of the URL to access it when it is available. Making a request to the full URL including the path will call the API you define within it. APIs can be defined to take either `get` or `post` requests.

Each API contains an `operationId` (which becomes the name of the method in the Java code), a list of `parameters`, a tag, and a list of possible `responses` that the API returns when it is called. At least a `200` response (indicating a successful request) is required for the API to work.

```
paths:
    "/entities/{userId}":
        get:
            operationId: getBasicUser
            parameters:
                - in: path
                  name: userId
                  required: true
                  schema:
                      type: integer
            responses:
                200:
                    content:
                        application/json:
                            schema:
                                $ref: "#/components/schemas/BasicUser"
                        application/xml:
                            schema:
                                $ref: "#/components/schemas/BasicUser"
                    description:
                        "The BasicUser object"
            tags: ["BasicUser"]
```

The `parameters` each have a `name` (which is used both in specifying it in a request as well as in the Java method call), whether it is required, and a `schema` (which may be either a basic data type or a schema for a custom object you have defined).

The `responses` each need to have `content` defined for both JSON and XML responses. Each of the possible responses must also define a `schema`, just like the parameters.

```note::
   If you want to use an object you have defined in the ``components`` section for either your API's parameters or return type, then you can reference it with ``$ref: "#/components/schemas/<ObjectName>"``.
```

The tag specifies what prefix REST Builder will use when generating the resource files for your implementation. In the above example, the API implementation would belong in the `BasicUserResourceImpl` file.

## Create Your Own APIs with REST Builder

Now that you understand how REST Builder works, you can use it to create APIs for your own applications. See the tutorial on [Implementing a New API with REST Builder](./implementing-a-new-api-with-rest-builder.md) to walk through this process with an example.