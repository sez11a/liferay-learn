# Installing a Search Engine

A search engine is a critical component of your Liferay DXP installation. Some information is provided in the [creating an example cluster](./../../installation-and-upgrades/setting-up-liferay-dxp/clustering-for-high-availability/example-creating-a-simple-dxp-cluster.md#prepare-a-search-engine), but this section will cover more of the details (though at a high level) you need for setting up a production system.

<!-- DIAGRAM -->

Liferay DXP includes an Elasticsearch server that runs in the same JVM. This makes local testing convenient since lots of Liferay DXP's functionality depends on a search engine, but the bundled server is but not supported in production environments.
<!-- Is this the place to introduce sidecar which will replace embedded in GA4? -->

Besides Elasticsearch, DXP also supports [Solr](http://lucene.apache.org/solr). Note that Solr is not embedded and has to be connected remotely. To use Solr, see the [Installing Solr](https://help.liferay.com/hc/articles/360032264052-Installing-Solr) article.
<!-- Is this the place to mention our deprecation of solr support? -->

## Prerequisites

### Java

The search engine and Liferay DXP must use the same Java version and distribution (e.g., Oracle Open JRE 1.8.0_201).

### Elasticsearch and Java Distribution Compatibility

Consult the [Elasticsearch compatibility matrix](https://www.elastic.co/support/matrix#matrix_jvm) and the [Liferay DXP compatibility matrix](https://help.liferay.com/hc/sections/360002103292-Compatibility-Matrix) to learn more about supported JDK distributions and versions.

### Solr and Java Distribution Compatibility

This consideration is not necessary for Solr, because no JVM level serialization happens between the servers. All communication occurs at the HTTP level.

## Enabling Clustering in the Search Engine

As note above, a production environment's search engine should be clustered. This enables the search engine to handle the load and optimizes DXP's overall performance. Both Elasticsearch and Solr can be configured successfully on multiple nodes in the remote environment.

* To configure a remote Elasticsearch server or cluster, see the [Installing Elasticsearch](https://help.liferay.com/hc/articles/360028711132-Installing-Elasticsearch) article.

* To configure a remote Solr server or cluster, see the [Installing Solr](https://help.liferay.com/hc/articles/360032264052-Installing-Solr) article.

## Using Solr

There are some drawbacks to using Solr as the search engine. These limitations affect how Solr processes search requests in various Liferay products.

### End User Feature Limitations of Liferay's Solr Integration

* [Liferay Commerce](https://help.liferay.com/hc/en-us/articles/360017869952)
* [Workflow Metrics](https://help.liferay.com/hc/en-us/articles/360029042071-Workflow-Metrics-The-Service-Level-Agreement-SLA-)
* [Custom Filter search widget](https://help.liferay.com/hc/en-us/articles/360028721272-Filtering-Search-Results-with-the-Custom-Filter-Widget)
* [The Low Level Search Options widget](https://help.liferay.com/hc/en-us/articles/360032607571-Low-Level-Search-Options-Searching-Additional-or-Alternate-Indexes)
* [Search Tuning: Customizing Search Results](https://help.liferay.com/hc/en-us/articles/360034473872-Search-Tuning-Customizing-Search-Results)
* [Search Tuning: Synonyms](https://help.liferay.com/hc/articles/360034473852-Search-Tuning-Synonym-Sets)

### Developer Feature Limitations of Liferay's Solr Integration

Implementation for the following APIs may be added in the future, but they are not currently supported by Liferay's Solr connector.

* From Portal Core (Module: `portal-kernel`, Artifact:
    `com.liferay.portal.kernel`):
  * `com.liferay.portal.kernel.search.generic.NestedQuery`
  * `com.liferay.portal.kernel.search.filter`:
    * `ComplexQueryPart`
    * `GeoBoundingBoxFilter`
    * `GeoDistanceFilter`
    * `GeoDistanceRangeFilter`
    * `GeoPolygonFilter`
* From the Portal Search API (Module: `portal-search-api`, Artifact:
    `com.liferay.portal.search.api`):
  * `com.liferay.portal.search.filter`:
    * `ComplexQueryPart`
    * `TermsSetFilter`
  * `com.liferay.portal.search.geolocation.*`
  * `com.liferay.portal.search.highlight.*`
  * `com.liferay.portal.search.query.function.*`
  * `com.liferay.portal.search.query.*`:
  * `com.liferay.portal.search.script.*`
  * `com.liferay.portal.search.significance.*`
  * `com.liferay.portal.search.sort.*`: only `Sort`,`FieldSort`, and `ScoreSort` are supported
* Portal Search Engine Adapter API (Module: `portal-search-engine-adapter-api`,
    Artifact: `com.liferay.portal.search.engine.adapter.api`)
  * `com.liferay.portal.search.engine.adapter.cluster.*`
  * `com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest`
  * `com.liferay.portal.search.engine.adapter.index.*`: only `RefreshIndexRequest` is supported
  * `com.liferay.portal.search.engine.adapter.search.*`:
    * `MultisearchSearchRequest`
    * `SuggestSearchRequest`
  * `com.liferay.portal.search.engine.adapter.snapshot.*`

Liferay Commerce requires the `TermsSetFilter` implementation which is only available in the Elasticsearch connector.
