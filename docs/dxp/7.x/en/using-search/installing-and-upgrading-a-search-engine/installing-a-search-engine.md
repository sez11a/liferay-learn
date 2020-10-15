# Installing a Search Engine

A search engine is a critical component of your Liferay installation. [Creating an Example Cluster](./../../installation-and-upgrades/setting-up-liferay-dxp/clustering-for-high-availability/example-creating-a-simple-dxp-cluster.md#prepare-a-search-engine) can get you started with the installation, but this guide demonstrates setting up a **production** environment.

<!-- MAKE A DIAGRAM SIMILAR TO THE CCR ONE BUT WITH JUST ONE CONNECTION -->

When you start Liferay, a built-in Elasticsearch server (sidecar) starts simultaneously. This default search engine provides search capabilities as a convenience for testing, but it isn't supported for use in production. [Getting Started with Elasticsearch](./elasticsearch/getting-started-with-elasticsearch.md) describes production-level Elasticsearch setup. [Using the Sidecar or Embedded Elasticsearch](./elasticsearch/using-the-sidecar-or-embedded-elasticsearch.md) describes the default Elasticsearch server (sidecar in 7.3 and embedded in 7.2) features and limitations.

```note::
   `Solr <http://lucene.apache.org/solr>`_ is now deprecated in 7.3. Though it can still be used, Solr is not bundled with Liferay and must be connected remotely, even for development and testing. To use Solr, see `Installing Solr <./solr/installing-solr.md>`_.
```

## Java Requirements

* Search engines require the `JAVA_HOME` environment variable. Set it on your search engine host.

* If you're using Liferay 7.2, Elasticsearch and Liferay must use the same Java version and distribution. Consult the [Elasticsearch compatibility matrix](https://www.elastic.co/support/matrix#matrix_jvm) and the [Liferay compatibility matrix](https://help.liferay.com/hc/sections/360002103292-Compatibility-Matrix) to learn more about supported JDK distributions and versions. You can specify this in Elasticsearch:

    ```properties
    [Elasticsearch Home]/bin/elasticsearch.in.sh`: `JAVA_HOME=/path/to/java`
    ```

The Java version and distribution requirement doesn't apply to Liferay 7.3 because the Elasticsearch 7 connector communicates via HTTP; there's no JVM level serialization. See the [Elastic's High-Level REST Client](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.x/java-rest-high.html) for details.

The same requirement doesn't apply to Solr either because the Solr connector also communicates via HTTP.

## Clustering the Search Engine

A production environment's search engine should be clustered for load management and optimal performance. Both Elasticsearch and Solr can be configured successfully on multiple nodes in the remote environment.

* To configure a remote Elasticsearch server or cluster, see [Getting Started with Elasticsearch](./elasticsearch/getting-started-with-elasticsearch.md).

* To configure a remote Solr server or cluster, see [Installing Solr](./solr/installing-solr/installing-solr.md).

## Selecting a Search Engine Vendor and Version

Elasticsearch is the recommended search engine for search and indexing with Liferay. Solr is deprecated and has [limitations](./solr/solr-limitations.md).

```important::
   Always refer to the `compatibility matrix <https://help.liferay.com/hc/en-us/sections/360002103292-Compatibility-Matrix>`_ to find the exact versions supported.
```

```note::
   If your installation requires a search engine besides Elasticsearch, whether as a replacement or in conjunction, you can write your own client application to provide a custom search engine connection. See the `Writing a Search Engine Adapter <../developer-guide/writing-a-search-engine-adapter.md>`_ for details.
```

## What's Next 

[Installing Elasticsearch](./elasticsearch/getting-started-with-elasticsearch.md) is recommended. If you must use Solr (deprecated), see [Installing Solr](./solr/installing-solr/installing-solr.md). 
