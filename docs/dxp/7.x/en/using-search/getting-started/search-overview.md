# Search Overview

Search is a fundamental component of Liferay. Elasticsearch is bundled with Liferay for testing and development purposes. Production environments require Elasticsearch running on a separate server (a side-car). [Getting Started with Elasticsearch](../installing-and-upgrading-a-search-engine/elasticsearch/getting-started-with-elasticsearch.md) explains Side-car setup. You can explore the search functionality immediately, however, using the bundled Elasticsearch engine.

Here are the search features:

* Full-text search
* Indexing of all content types (blogs, documents, web content, etc.)
* Highly configurable search pages
* A search bar embedded in the header of every page
* Automatic index synchronization for added, updated, and deleted content
* Search result filtering by roles and permissions
* Search suggestions
* Search result configuration (e.g., filter and sort)
* Faceted search
* Enable or disable advanced search syntax (e.g., AND/OR/NOT, wildcards, and more)

## Search Pages and Widgets

The default search page (`localhost:8080/search`) has a practical set of search widgets. It is customizable and based on the global search page template (also customizable). 

![The search page template is useful.](./search-overview/images/05.png)

To add search widgets to a page, click the Add button on the page, select _Widgets_, and open the Search category:

![There are plenty of search widgets.](./search-overview/images/07.png)

To learn more, visit [Configuring Search Pages](https://help.liferay.com/hc/en-us/articles/360028821052-Configuring-Search-Pages) or [Search Pages and Widgets](../search-pages-and-widgets/README.md).

## Search Configuration and Administration

In the Control Panel, you can configure search functionality, view connections and field mappings, and perform search index actions.

You can configure search at the system scope in the Control Panel. In the Configuration category, select *System Settings* &rarr; *Search*. The search settings page appears.

![Search is highly configurable.](./search-overview/images/06.png)

You can examine search connections and field mappings, and execute indexes in the search administration screens. Navigate them in the Control Panel, by selecting *Search* in the Configuration category.

![The search admin screens are informative and useful.](./search-overview/images/08.png)

Learn more at [Search Administration and Tuning](../search-administration-and-tuning/README.md).

## Custom Development in Search

Search customization usually involves at least on of these search phases:

**Indexing** is the sending one or more documents to the search engine. The document contains fields of various types (text, keyword, etc.). The search engine processes each field and determines whether to store the field or analyze it.

**Searching** is sending a search query and obtaining results (a.k.a. hits) from the search engine. Queries and filters can be part of the search request, both of which specify a field to search within and the value to match against. The search engine iterates through each field within the nested queries and filters, and optionally performs special analysis prior to executing the query (search time analysis). Search time analysis can be configured for each field via the mapping definitions.

Search functionality can be extended and invoked using is Service Provider Interfaces and APIs, respectively. 

* Service Provider Interfaces (SPIs) are meant to be implemented. In the source code, these are found in modules ending in `-spi` (for example, the [`portal-search-spi` module](https://github.com/liferay/liferay-portal/tree/[$LIFERAY_LEARN_PORTAL_GIT_TAG$]/modules/apps/portal-search/portal-search-spi)).

* APIs contain methods you can call in your own code. In the source code, these are found in modules ending in `-api` (for example, the [`portal-search-api` module](https://github.com/liferay/liferay-portal/tree/[$LIFERAY_LEARN_PORTAL_GIT_TAG$]/modules/apps/portal-search/portal-search-api)).

See the Developer Guide for details.

## What's Next 

Explore the search features by [Searching for Content](./searching-for-content.md). When you're ready to configure search for production, see [Getting Started with Elasticsearch](../installing-and-upgrading-a-search-engine/elasticsearch/getting-started-with-elasticsearch.md).

<!--

Search is a fundamental component of Liferay DXP. If you're testing out the built-in search functionality or developing, there's a [search engine bundled](#elasticsearch) precisely for these purposes. Just start the portal and begin searching. In production environments, you must first install a search engine and configure Liferay DXP to begin using search.

Once a search engine holding your indexed data is freely communicating with Liferay DXP, you're ready to configure or customize the search experience.

Sites often feature lots of content split over lots of asset types. Web content articles, documents and media files, and blogs entries are just a few examples. Most content types are *assets*.Under the hood, assets use the [Asset API](https://help.liferay.com/hc/en-us/sections/360004656831-Asset-Framework) and [indexing code](#custom-development-in-search). Any content that has these features can be searched in Liferay DXP's out-of-the-box search widgets. 

![The Type Facet configuration lists the searchable out-of-the-box asset types.](./search-overview/images/01.png)

## Bundled Search Features

A bunch of search widgets are bundled with Liferay DXP:

- Search Bar
- Search Results
- Search Facets
- Custom Filter
- Search Insights
- Low Level Search Options
- Similar Results (bundled in 7.3+)
- Sort
- Search Options
- Suggestions
- X-Pack Monitoring (LES)

![Compose the search widgets to build your search page any way you see fit.](./search-overview/images/05.png)

Administrative search functionality is also included:

- Search Tuning
- Search Engine Connection Configuration
- System Level Search Configurations
- Adding and editing Search pages
- Viewing Indexes and Field Mappings
- Indexing Actions

![Much of the administrative configuration is done via System Settings.](./search-overview/images/06.png)

The behavior and configuration of these features is described in the User Guide section.

## Elasticsearch

The default search engine is Elasticsearch, which is backed by the Lucene search library. There's an Elasticsearch server embedded in all bundles, which is handy for testing and development purposes. Production environments must install a separate, remote Elasticsearch server (or even better, cluster of servers).  For information on how to install Elasticsearch, read the [deployment guide](https://help.liferay.com/hc/en-us/sections/360004655831-Installing-a-Search-Engine).

Actually the rest of this stuff is probably unnecessary here. Preserving for now in a commented out section -->
<!--
## Searching

Find a search bar (there's one embedded in every page by default), enter a term, and click *Enter*.

![There's a search bar embedded on all pages by default.](./search-overview/images/02.png)

After search is triggered, a results page appears. If there are hits to search engine documents, they appear as search results in the right hand column. In the left hand column are search facets.

![Results are displayed in the Search Results portlet.](./search-overview/images/03.png)

The search bar, search results, and search facets make up three powerful features in the search UI.

### Search Bar

The search bar is where you enter *search terms*. Search terms are the text you send to the search engine to match against the documents in the index.

### Search Results and Relevance

The search term is processed by an algorithm in the search engine, and search results are returned to users in order of relevance. Relevance is determined by a document's *score*, generated against the search query. The higher the score, the more relevant a document is considered. The particular relevance algorithm used is dependent on [algorithms provided by the search engine (Elasticsearch by default)](https://www.elastic.co/guide/en/elasticsearch/guide/current/relevance-intro.html#relevance-intro).

### Search Facets

Facets allow users of the Search application to filter search results. Think of facets as buckets that hold similar search results. You might want to see the results in all the buckets, but after scanning the results, you might decide that the results of just one bucket better represent what you want. So what facets are included out of the box?

- Category
- Folder
- Site
- Tag
- Type
- User
- Modified
- Custom

![Site and Type are two of the facet sets you'll encounter. They let you drill down to results that contain the search terms you entered.](./search-overview/images/04.png)

You've probably used something similar on any number of sites. You search for an item, are presented with a list of results alongside a list of buckets you can click to further drill down into the search results, without entering additional search terms. Search facets work the same way.
-->
