# Custom Filter Examples


The Custom Filter widget is a powerful aid to your search tuning efforts. Without deploying custom code, you can exert control over the query sent to the search engine. Some common use cases are presented here to help you understand how to approach the Custom Filter widget:

- [Excluding Content from Search Results](#excluding-certain-content)
- [Boosting Content in Search Results](#boosting-fields)
- [Filtering by multiple Site IDs](#filtering-by-site-id)
- [Using Elasticsearch's Query String query](#complex-filter-with-query-string) 
- [Boosting Matches to Nested Fields](#boosting-matches-to-nested-fields)

See [Filtering Search Results](./filtering-search-results.md) for a detailed explanation of the Custom Filter widget.

## Excluding Certain Content

When used with a `must_not` Occur configuration, the Custom Filter can catch and exclude documents from the Search Results.

### Excluding Certain Documents and Media Content

Sometimes, you may want to exclude certain types of content from appearing in the Search Results. To exclude Documents and Media file entries that are only present in the system to be added to Web Content, you must first distinguish the particular files to exclude, in a way that they can be identified in the search index. You can tag them with something that declares their purpose (`wconly` perhaps), or you can put them into a dedicated [Documents and Media Folder](./../../../content-authoring-and-management/documents-and-media/uploading-and-managing/creating-folders.md ). To configure a Custom Filter to exclude a Documents and Media Folder, use these settings:

**Filter Query Type:** `Match`

**Filter Field:** `folderId`

**Filter Value:** `41103`

**Occur:** `must_not`

This configuration ensures that search documents containing a `folderId` field with the value `41103` are not returned in the search results.

### Excluding Content with Certain Extensions

Perhaps you must exclude GIF files the search results. Configure a Custom Filter like this:

**Filter Query Type:** `Match`

**Filter Field:** `extension`

**Filter Value:** `gif`

**Occur:** `must_not`

This configuration leverages the presence of the `extension` field indexed from the Documents and Media [`DLFileEntry` model](https://github.com/liferay/liferay-portal/blob/[$LIFERAY_LEARN_PORTAL_GIT_TAG$]/modules/apps/document-library/document-library-service/src/main/java/com/liferay/document/library/internal/search/spi/model/index/contributor/DLFileEntryModelDocumentContributor.java). It ensures that search documents containing an `extension` field with the value `gif` are not returned in the search results.

## Boosting Fields

Boosting certain documents based on specific fields is a common need, and it's readily accomplished with the Custom Filter widget. The boost value often needs tuning to meet your needs. Use the Search Insights widget with _Enable Score Explanation_ enabled to inspect how the documents are being scored and to fine-tune your boost values.

### Boosting Matches to Designated Fields

To boost a document with certain fields that match the searched keywords, configure a Custom Filter like this:

**Filter Query Type:** `Multi Match`

**Filter Field:** `title_en_US, content_en_US`

**Occur:** `Should`

**Boost:** `100`

**Custom Parameter Name:** `q`

This configuration boosts matching documents if the English (United States) title and content contains the keywords entered by the user in the Search Bar widget. Entering the Custom Parameter Name with the same value as the Search Bar's Keywords Parameter Name configuration means that the value passed into the Search Bar is the value that's boosted by the Custom Filter (if it matches with documents in the search index).

The Multi Match query can match multiple fields at once. Otherwise you'd need to configure a Custom Filter for each separate field, even if the rest of the configuration values are identical.

### Boosting by a Field's Presence

To boost any content that's tagged, regardless of what the tag value is, configure a Custom Filter like this:

**Filter Query Type:** `Exists`

**Filter Field:** `assetTagNames`

**Occur:** `should`

**Boost:** `100`

If a document matching the query is tagged, it contains a `assetTagNames` field. The Exists query matches on any value for the field. 

## Filtering by Site ID

There's no configuration for searching multiple Sites on one Search Page without searching all of them. To include results from the current Site and all [child Sites](../../../site-building/building-sites/site-hierarchies.md) you must configure the Search Bar's Scope, setting it to _Everything_. After that, you'll use one parent Custom Filter with a Bool query that can collect child query clauses, each of which is contributed by a Custom Filter widget with a term query for matching the `groupId` of a Site to include in the search results. The Site's ID is the `groupId` field in the search document.

1. Create 3 Sites:
   - Create at least one parent Site with a child Site.
   - Create at least one additional Site that won't be included in the search.

   ```tip::
      To find the Group ID of a Site, in the site menu navigate to Configuration > Settings. The displayed `Site ID` is the ``groupId`` you can use to filter the Site.
   ```

1. Create at least one piece of content in each Site (a Blogs Entry), and include the word _Liferay_ in each.

1. Set the Search Bar scope to _Everything_.

   > **Checkpoint:** Search and see that content from all the sites is returned

   ![These three Blogs Entries are each from a different Site.](./custom-filter-examples/images/01.png)

1. Configure a parent Custom Filter:

   **Filter Query Type:** `Bool`

   **Occur:** `Filter`

   **Query Name:** `SiteBoolQuery`

1. Configure a Custom Filter for each Site to include in the search:

   **Filter Query Type:** `Term`

   **Filter Field:** `groupId`

   **Occur:** `should`

   **Parent Query:** `SiteBoolQuery`

   **Filter Value:** `38109`

   For the example content form the above screenshot, one more Custom Filter is needed. Configure it identically to the above with one exception: the Filter Value is `38105`.

   > **Checkpoint:** Search again and confirm that content from only the designated Sites is returned. This can be further confirmed by looking at the details view if the search results are configured to _Display Results in Document Form_.

   ![Only content from the included Sites is displayed.](./custom-filter-examples/images/02.png)

Importantly, the filters by `groupId` declare the `SiteBoolQuery` as the parent query. The _should_ Occur clauses in the child Term queries (for each Site) act as an OR operator, so that if any of the `groupId`s are matched, their content can be displayed in the Search Results widget.

## Complex Filter with Query String

Sometimes you can avoid the need for multiple queries (as in the [Filtering by Site ID](#filtering-by-site-id) example), by using the [Query String query](https://www.elastic.co/guide/en/elasticsearch/reference/7.x/query-dsl-query-string-query.html). The below configuration demonstrates how you can use only one Custom Filter widget to constrain the search to

* Match Documents and Media files with `pdf` or `jpg` extensions OR
* Match Web Content Articles

Configure the Custom Filter widget like this:

**Filter Query Type:** `Query String`

**Occur:** `Filter`

**Filter Value:** `((extension:pdf OR extension:jpg) AND entryClassName:com.liferay.document.library.kernel.model.DLFileEntry) OR entryClassName:com.liferay.journal.model.JournalArticle`

Using parentheses is recommended to ensure that your intended precedence is enforced.

Simplifying the configuration of a search page (often a complex case is handled with just one Custom Filter widget) is nice, but the Query String query is not the answer to all complexity in the custom Filter widget. Some queries cannot be mimicked using the Query String type. For example, it cannot handle Nested queries to search nested documents, Term queries to avoid analysis, or Prefix queries to search based on prefixes.

```warning::
   The Query String query should not be used if the value being passed is coming from the search bar (as demonstrated in `Boosting Matches to Designated Fields`_. If the Search Bar's user enters a keyword containing invalid syntax, an error is returned.
```

## Boosting Matches to Nested Fields

> Availability: 7.2 FP10+, 7.3 FP1/SP1+

As described in [Accessing Nested DDM Fields](../search-facets/custom-facet.md#accessing-nested-ddm-fields) (in the Custom Facets article), DDM Fields became [nested fields](../../../liferay-internals/reference/7-3-breaking-changes.md#dynamic-data-mapping-fields-in-elasticsearch-have-changed-to-a-nested-document) as of Liferay 7.2 SP3+/FP8+ (and on all Liferay 7.3 versions). On the latest Fix Pack and GA release of 7.2 and 7.3, the [Elasticsearch Nested query](https://www.elastic.co/guide/en/elasticsearch/reference/7.x/query-dsl-nested-query.html) is supported to account for these nested fields.

Using a [nested field](../../../liferay-internals/reference/7-3-breaking-changes.md#dynamic-data-mapping-fields-in-elasticsearch-have-changed-to-a-nested-document) in a Custom Filter configuration requires three Custom Filter widgets on the search page.  A [Nested query](https://www.elastic.co/guide/en/elasticsearch/reference/7.x/query-dsl-nested-query.html) is added in one of the widgets, for wrapping the required child queries: one child query matches the field's name, the other the value.

This example demonstrates adding a boost for matches to a certain DDM Structure field. 

1. Create a [Structure](../../../content-authoring-and-management/web-content/web-content-structures/creating-structures.md):
    - In the Site menu, go to Content & Data &rarr; Web Content.
    - Click the _Structures_ tab, then the Add button ![Add](../../../images/icon-add.png).
    - Give the structure a Title (e.g., _Boosted Content_) and these fields:
        - Field 1:
            - **Type:** `Boolean`
            - **Field Label:** `Boost?`
        - Field 2:
            - **Type:** `Text Box`
            - **Field Label:** `Content`
    - Save the Structure.

    Structure fields are indexed by default.

1. Add one Web Content that uses the new Structure.

    - **Title:** `Boosted`
    - **Boost?** `True`
    - **Content:** `Test.`

1. Add a second Web Content that uses the new Structure.

    - **Title:** `Not Boosted`
    - **Boost?** `False`
    - **Content:** `Test content.`

1. Go to the search page and search for _test content_.

    **Checkpoint:** Because of the exact match to the content field, the Not Boosted Web Content appears before the Boosted Web Content.

1. In the Kibana Dev Tools console, or from the CLI via cURL, execute a GET request to find DDM fields with `Boost` in the field name:

    ```json
    GET liferay-20097/_search
    {
      "query": {
        "nested": {
          "path": "ddmFieldArray",
          "query": {
            "wildcard":  { "ddmFieldArray.ddmFieldName": "ddm__*Boost*" }
          }
        }
      }
    }
    ```

    Replace the `20097` with the `companyId` of your [Virtual Instance](../../../system-administration/configuring-liferay/virtual-instances/understanding-virtual-instances.md).

1. In the Elasticsearch response, find and copy the `ddmFieldArray` with its nested Boost field:

    ```json
    "ddmFieldArray" : [
                {
                  "ddmFieldName" : "ddm__keyword__39707__Boost_en_US",
                  "ddmValueFieldName" : "ddmFieldValueKeyword_en_US",
                  "ddmFieldValueKeyword_en_US" : "true",
                  "ddmFieldValueKeyword_en_US_String_sortable" : "true"
                }
    ```

1. Go to the search page and add three Custom Filters using the Elasticsearch response data:

    - Filter 1, the parent nested query:
        - **Filter Field:** `ddmFieldArray`
        - **Filter Query Type:** `Nested`
        - **Occur:** `should`
        - **Query Name:** `parent_query`
        - **Boost:** `500`
    - Filter 2, the child match query for the field name.
        - **Filter Field:** `ddmFieldArray.ddmFieldName`
        - **Filter Query Type:** `Match`
        - **Occur:** `should`
        - **Value:** `ddm__keyword__39707__Boost_en_US`
        - **Parent Query Name:** `parent_query`
    - Filter 3, the child match query for the value of `true` in the Boost field:
        - **Filter Field:** `ddmFieldArray.ddmFieldValueKeyword_en_US`
        - **Filter Value:** `true`
        - **Filter Query Type:** `Match`
        - **Occur:** `should`
        - **Parent Query Name:** `parent_query`

1. Now repeat the search for _test content_ and verify that the Boosted Web Content appears above the Not Boosted Web Content.

The boost value often needs tuning to meet your needs. Use the Search Insights widget with _Enable Score Explanation_ enabled to inspect how the documents are being scored and to fine-tune your boost values.

## Related Content

- [Filtering Search Results](./filtering-search-results.md)
- [Result Rankings](../../search-administration-and-tuning/result-rankings.md)
- [Synonym Sets](../../search-administration-and-tuning/synonym-sets.md)

