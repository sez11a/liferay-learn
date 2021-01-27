# Overriding OSGi Services

Liferay's OSGi container is a dynamic environment in which services can be added, removed, or overridden as needed. This framework registers Liferay components with the OSGi service registry, each with their own availability, ranking, and attributes. Together, these details determine whether components referring to a service type bind to that particular service or not.

When attempting to override an OSGi service, follow these steps:

1. Identify the service you want to override, and gather its essential service and reference details.

1. Use these details to create a custom service for overriding the existing service.

1. Reconfigure Liferay components to use your custom service (if needed).

The following tutorial uses [sample modules]() to demonstrate how to override an OSGi service.<!--ADD PATH--> These modules include an `api`, default `impl`, and generic `portlet` that consumes the implementation. A custom `impl` and `config` file are also included for overriding the default implementation and reconfiguring the portlet to use the custom implementation.

## Deploy the Sample Modules for Overriding

Follow these steps to download, build, and deploy the sample modules:

1. Start a new [Liferay Docker container](../../../installation-and-upgrades/installing-liferay/using-liferay-docker-images/docker-container-basics.md).

   ```bash
   docker run -it -p 8080:8080 [$LIFERAY_LEARN_DXP_DOCKER_IMAGE$]
   ```

1. Download and unzip the example modules.

   ```bash
   curl https://learn.liferay.com/dxp/7.x/en/liferay-internals/extending-liferay/overriding-osgi-services/reconfiguring-components-to-use-your-osgi-service/resources/liferay-m1t1.zip -O
   ```

   ```bash
   unzip liferay-m1t1.zip -d liferay-m1t1
   ```

1. Run the following command in the project's root folder (i.e., `liferay-m1t1`) to build `jar` files for the sample modules.

   ```bash
   ./gradlew jar
   ```

   Each module's JAR is generated in its `build/libs` folder (e.g., `m1t1-api/build/libs/com.acme.m1t1.api-1.0.0.jar`).

1. Deploy the `api`, `able.impl`, and `portlet` JARs to your Docker container.

   ```bash
   docker cp m1t1-api/build/libs/com.acme.m1t1.api-1.0.0.jar $(docker ps -lq):/opt/liferay/deploy
   ```

   ```bash
   docker cp m1t1-able-impl/build/libs/com.acme.m1t1.able.impl-1.0.0.jar $(docker ps -lq):/opt/liferay/deploy
   ```

   ```bash
   docker cp m1t1-web/build/libs/com.acme.m1t1.web-1.0.0.jar $(docker ps -lq):/opt/liferay/deploy
   ```

   Log messages indicate when Liferay begins processing and successfully starts each module. These Logs also provide each service's bundle id.

   ```shell
   STARTED com.acme.m1t1.api_1.0.0 [1357]
   STARTED com.acme.m1t1.able.impl_1.0.0 [1358]
   STARTED com.acme.m1t1.web_1.0.0 [1359]
   ```

   Once deployed, each module is registered with the OSGi service registry. The provided `api` defines an OSGi service type that is then implemented by the `able.impl` module, which in turn is consumed by the provided `portlet`.<!--Add in user instruction to confirm this via the Gogo Shell?-->

1. Deploy the `baker.impl` JAR to your Docker container.

   ```bash
   docker cp m1t1-baker-impl/build/libs/com.acme.m1t1.baker.impl-1.0.0.jar $(docker ps -lq):/opt/liferay/deploy
   ```

1. Deploy the system configuration file to ensure the deployed portlet uses the `baker.impl` component to override and delegate to `able.impl`.

   ```bash
   docker cp com.acme.m1t1.web.internal.portlet.M1T1Portlet.config $(docker ps -lq):/opt/liferay/deploy/osgi/configs
   ```
   <!--Add in user instruction to confirm this via the Gogo Shell?-->

<!-- Add in conclusion and/or transition? -->

## Gathering an OSGi Service's Details

Once you've identified the service you want to override, use Gogo Shell commands to gather the following service and service reference details:

* **Service Type**: Your custom OSGi service must implement the service type as the component you want to override.

* **Component Name**: You can invoke the existing service with your custom service using the target service's `component.name`.

* **Reference Configuration Details**: A service's reference configuration details determine a service's conditions for adopting another service and whether you'll need to reconfigure other services to use your custom service or not. You'll need to collect the following details:

  * **Component Name**: the name of the component referencing the service you're overriding

  * **Reference Name**: the `Reference` value

  * **Reference Policy**: whether the reference is `static` or `dynamic`

  * **Reference Policy Option**: whether the reference is `greedy` or `reluctant`

  * **Cardinality**: the number of service instances to which the reference can bind

The following example runs the `scr:info` Gogo Shell command for the sample `portlet` to gather details for overriding the `able.impl` OSGi service.

```shell
scr:info com.acme.m1t1.web.internal.portlet.M1T1Portlet
```

```
Component Description: com.acme.m1t1.web.internal.portlet.M1T1Portlet
=====================================================================
Class:         com.acme.m1t1.web.internal.portlet.M1T1Portlet
...

Component Configuration Id: 8131
--------------------------------
...
References:   (total 1)
  - _m1T1: com.acme.m1t1.M1T1 SATISFIED 1..1 static
    target=(*) scope=bundle (1 binding):
    * Bound to [4516] from bundle 1358 (com.acme.m1t1.able.impl:1.0.0)
```

In this example, `M1T1Portlet` has an `_m1T1` field that references and is bound to a component of the `M1T1` service type in the `com.acme.m1t1.able.impl:1.0.0` bundle. Searching for this bundle in the Gogo Shell (e.g., `bundle 1358`) reveals the full name of the component consumed by the portlet: `com.acme.m1t1.able.internal.M1T1AbleImpl`.

Finally, the portlet's reference policy is `static` with the default `reluctant` reference policy option, and its cardinality is mandatory and unary (i.e., `1..1`). Together, these details indicate the portlet requires reconfiguration to use a new `impl` in place of `M1T1AbleImpl`. See [Reconfiguring Components to Use Your OSGi Service](#reconfiguring-components-to-use-your-osgi-service) for more information.

<!-- Add transition? Remove summary? -->

* **Service Type**: `M1T1`

* **Service's Class Name**: `com.acme.m1t1.internal.M1T1Impl`

* **Reference Configuration Details**: Reconfiguring the referencing portlet service is necessary.
  * **Component Name**: `com.acme.m1t1.web.internal.portlet.M1T1Portlet`

  * **Reference Name**: `_s1J6`

  * **Reference Policy**: `static` (default)

  * **Reference Policy Option**: `reluctant` (default)

  * **Cardinality**: mandatory and unary (i.e., `1..1`)

## Creating a Custom OSGi Service

After gathering the requisite service details, you can create a custom OSGi service to override the existing service. Ensure your custom service follow these steps:

1. Implement the same service type as the target OSGi service.

2. Add the `@Component` annotation and `service` attribute to make your class a Declarative Service (DS) component, which registers your class as an OSGi service available in the OSGi service registry.

3. Include the `service.ranking:Integer` component `property` to rank your service higher than existing services.

4. Override the service type's methods.

5. (Optional) Reference the existing service's `component.name` to invoke it.

When ready, deploy the custom service to your Liferay instance to register it with Liferay's OSGi runtime framework.

The sample `M1T1BakerImpl` module is provided to override `M1T1AbleImpl`.

```java
@Component(property = "service.ranking:Integer=100", service = M1T1.class)
public class M1T1BakerImpl implements M1T1 {

    @Override
    public String doSomething() {
        StringBuilder sb = new StringBuilder();

        sb.append(
            "M1T1BakerImpl, which delegates to " +
                _defaultService.doSomething());

        return sb.toString();
    }

    @Reference(
        target = "(component.name=com.acme.m1t1.able.internal.M1T1AbleImpl)",
        unbind = "-"
    )
    private M1T1 _defaultService;

} 
```

Here, `M1T1BakerImpl` implements the same service type as `M1T1AbleImpl` (i.e., `M1T1`) and includes the necessary `@Component` annotation, `service` attribute, and `service.ranking` property. It also references the existing service (i.e., `component.name=com.acme.m1t1.able.internal.M1T1AbleImpl`) and delegates to it as part of overriding the interface's method.

However, getting components to adopt your custom service immediately can require reconfiguring their references.

## Reconfiguring Components to Use Your OSGi Service

Together, a service's [Reference Policy](https://docs.osgi.org/javadoc/r5/enterprise/org/osgi/service/component/annotations/ReferencePolicy.html) (i.e., `static` or `dynamic`), [Reference Policy Option](https://docs.osgi.org/javadoc/r5/enterprise/org/osgi/service/component/annotations/ReferencePolicyOption.html) (i.e., `reluctant` or `greedy`) and [Cardinality](https://docs.osgi.org/specification/osgi.cmpn/7.0.0/service.component.html#service.component-reference.cardinality) (i.e., unary or multiple, and optional or mandatory) determine a component's conditions for adopting new services. See the above links for detailed explanations of each category.

In cases where the reference's policy option is `greedy`, the reference does not need to be reconfigured to adopt your new service, provided its ranking is higher than the service you want to override.

However, if the policy is `static` and its policy option is `reluctant`, the referencing service requires reconfiguration.

Follow these steps:

1. Create a [system configuration file]() named after the referencing component. Follow the name convention `[component].config`. <!--Add path to Understanding System Configuration Files once it is ported-->

1. Add configuration information to the file using the following format:

   ```
   [reference].target=[filter]\=[target]
   ```

   Replace `[reference]` with the reference's name (e.g., _`m1T1`).

   Replace `[filter]` with the desired filter (e.g., `component.name`).

   Replace `[target]` with the target component you want your service to use (e.g., com.acme.m1t1.baker.internal.M1T1BakerImpl).

1. Optionally, use a `cardinality.minimum` entry appended to the reference name to specify the number of services the reference can use. Ensure it follows this format:

   ```
   [reference].cardinality.minimum=[int]
   ```

Once your configuration file is prepared, deploy it to your Liferay instance to ensure your new service is used in place of the old.

The sample modules includes a config file named `com.acme.m1t1.web.internal.portlet.M1T1Portlet.config`, which includes the following script:

```
_m1T1.target="(component.name\=com.acme.m1t1.baker.internal.M1T1BakerImpl)" 
```
<!-- Add Conclusion? -->

## Additional Information

* []()
* []()
* []()
