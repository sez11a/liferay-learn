# Cross-Cluster Index Replication

> **LES Subscribers**

In a classic Liferay DXP/search engine installation, one Liferay DXP cluster talks to one Elasticsearch cluster, sending all of its read (e.g., execute a search query) and write (e.g., create a document) requests through one connection to the search engine cluster. All search engine servers were located in the same data center (though they could be in a different data center from the Liferay DXP servers).

<!--DIAGRAM?-->
<!-- Something like this but with Read/Write arrows drawn: https://docs.google.com/presentation/d/1MQ0Hygi4TaBHu7oz3sbBftm7ateN5CkLIEZsvFTJUFY/edit#slide=id.g635bb9f6f3_0_742-->

Responding to concerns about data locality and disaster recovery, Elasticsearch released a [Cross-Cluster Replication (CCR)](https://www.elastic.co/guide/en/elasticsearch/reference/current/xpack-ccr.html) feature that can be used with Liferay DXP, for both Elasticsearch 6 and 7 (refer to the [LES compatibility matrix](https://www.liferay.com/compatibility-matrix/liferay-enterprise-search) for version compatibility details). With CCR you can achieve a limited form of multi-data-center deployment, in that it does not allow distributing an Elasticsearch cluster's nodes over multiple data centers, but does allow configuring a separate Elasticsearch cluster in each data center. In this setup, there is at least one cluster containing _leader_ indexes and one cluster containing _follower_ indexes that are replicated from leader and only ever used by Liferay DXP for reading data.

Liferay DXP has long supported the idea of a distributed cluster, with nodes in disparate locations, via Wide Area Network (WAN) protocols. Liferay DXP's flexibility and Elasticsearch's support for Cross-Cluster Replication can support different system designs.

<!-- We'll do this in a follow-up effort
_Two Data Centers: one designed as a disaster recovery data center_
A Production data center can hold the leader and some DXP nodes, with secondary data center (perhaps primarily used for disaster recovery) holding the follower and some additional DXP nodes.

<!--DIAGRAM 1 (the setup we'll reate in the next article) -->
<!-- TODO: Basically repackage this drawing: https://docs.google.com/presentation/d/1MQ0Hygi4TaBHu7oz3sbBftm7ateN5CkLIEZsvFTJUFY/edit#slide=id.g80fc239334_0_0 -->

_More than Two Data Centers: chained replication_
Multiple Data centers, chained replication or with all separately replicating directly from the leader. A chained replication scheme involving more than two data centers. Each data center may or may not have any DXP nodes.

<!--DIAGRAM 2--> <!--TODO: Draw inspiration from https://www.elastic.co/blog/cross-datacenter-replication-with-elasticsearch-cross-cluster-replication, More than Two Data Centers-->

_Two Data Centers: Bi-Directional Replication_
Each data center holds a leader index and a follower index. All writes happen to the remote and all reads form the local. How do they sync? Would DXP nodes from each cluster need to write simultaneously to make sure the leaders are in sync?

<!--DIAGRAM 3 --><!-- TODO;  https://www.elastic.co/blog/cross-datacenter-replication-with-elasticsearch-cross-cluster-replication, Bi-Diriecitonal Replication-->

_Many Clusters: Replicate many smaller indexes into a centralized reporting cluster_
<!-- DIAGRAM 4 --> 
Lots of clusters with independent indexes replicating them to a centralized cluster where all the data can be consumed for analysis and reporting; DXP could be running locally or remotely at each disparate data center, which is more likely?) <!-- TODO-->
-->

No matter what setup you decide on, you must:

- Install the CCR Module on Liferay DXP
- Decide what indexes to replicate from the leader cluster
- Configure the Elasticsearch Clusters
- Configure the Liferay DXP Cluster
- Define the Leader and Follower
- Replicate the Leader Indexes

> **Elasticsearch 6 Only:** Enable [soft deletes](https://www.elastic.co/guide/en/elasticsearch/reference/6.7/ccr-requirements.html) for all existing indexes. Read [here](./enabling-soft-deletes.md) to learn more about this.

Read this article for an overview, then see the next article, [a step-by-step tutorial](./configuring-cross-cluster-replication-step-by-step.md), to get a local example up and running.

## Liferay DXP: Install the LES Cross-Cluster Replication Module

<!-- Does a docker container constitute a single node in a cluster? if so we'll need to instruct to use the mp app in control panel to install the ccr module into each dxp node that will use a read-only connection to the follower cluster. -->

Any Liferay DXP node that will read from a local cluster's follower indexes and write through a separate connection to the remote cluster's leader indexes, must have the CCR module installed. This module is available with your Liferay Enterprise Search subscription.

Any Liferay DXP node that will read and write from the same cluster does not need the CCR module installed. Simply configure a normal REMOTE mode connection to the Elasticsearch cluster.

##  Liferay DXP: Decide Which Indexes to Replicate from the Remote Cluster

<!-- If you know you need to replicate all the indexes in the cluster that holds your leader indexes, you don't have to do anything. Liferay's CCR support will handle the automatic replication of all indexes with no configuration needed. By default, all indexes present in the Elasticsearch cluster will be replicated. In most cases, this is the desirable situation. 

If you need to exclude some indexes from being replicated, you must blacklist the indexes to exclude before enabling Cross-Cluster Replication. For example, if you have another system indexing data into the same Elasticsearch cluster you're setting up for Liferay DXP, and you do not wish for those indexes to be replicated to the cluster holding follower indexes, blacklist them before you enable Cross-Cluster Replication.

```note::
   Liferay DXP contains APIs for creating and using (writing to and reading from) Elasticsearch indexes that remain completely under your control. See the Developer Guide for information on these APIs. These types of custom indexes (and any other indexes that might be in your Elasticsearch cluster) are replicated from your leader cluster by default, unless you blacklist them (see the blacklisting instructions contained in this guide).
```
-->

The default Liferay DXP indexes in your installation approximate the list below (subject to change):
<!-- This table is probably good info to include somewhere more central, not just in CCR docs -->

| Index ID                                      | Index Type    | Index Purpose |
| --------------------------------------------- | ------------- | ------------- |
| liferay-0                                     | System Index  | Searching in the System Settings application |
| liferay-20101                                 | Company Index | Searching the indexed assets of Liferay DXP |
| liferay-search-tuning-rankings                | App Index     | Primary data storage for the Result Rankings application |
| liferay-search-tuning-synonyms-liferay-20101  | App Index     | Primary data storage for the Synonym Sets application |
| workflow-metrics-instances                    | App Index     | Store data about Workflow Instances for the Workflow Metrics application |
| workflow-metrics-nodes                        | App Index     | Store data about Workflow Nodes for the Workflow Metrics application |
| workflow-metrics-processes                    | App Index     | Store data about Workflow Processes for the Workflow Metrics application |
| workflow-metrics-sla-instance-results         | App Index     | Primary storage for SLA results per Workflow Instance for the Workflow Metrics application |
| workflow-metrics-sla-task-results             | App Index     | Primary storage for SLA results per Workflow Task for the Workflow Metrics application |
| workflow-metrics-tokens                       | App Index     | Store data about Workflow Tokens for the Workflow Metrics application |
| your-custom-index-[companyId]                 | Dev Index     | Your own storage and searching needs |

Unless your setup reveals a very compelling reason not to, you should replicate all of the Liferay DXP indexes and all of your custom Dev Indexes into the follower Elasticsearch cluster. 

## Configure the Elasticsearch Clusters

Set up the Elasticsearch clusters, using versions supported with Liferay DXP. See the [LES compatibility matrix](https://help.liferay.com/hc/en-us/articles/360016511651-Liferay-Enterprise-Search-Compatibility-Matrix?flash_digest=189c5acefca397bdd5f06a6e0f0d2059ef4fa368) for details.

Make sure you Install the Elasticsearch [plugins Liferay DXP needs](https://help.liferay.com/hc/en-us/articles/360028711132-Installing-Elasticsearch#step-three-install-elasticsearch-plugins), and provide cluster names to differentiate your follower and leader cluster.

CCR requires an Elasticsearch Platinum level license, but [LES customers](./introduction-to-les.md)) already have this. If you're testing locally, you'll need to install a [trial license](https://www.elastic.co/guide/en/elasticsearch/reference/7.x/start-trial.html) on each cluster.

## Configure the Liferay DXP Cluster 

Configure the Liferay Clustering behavior first. In the example provided in the tutorial, some configuration will be provided for testing purposes. See the [clustering documentation](LINK) for more information on setting up a production cluster.

### Configure the Nodes that Reside with the Leader Indexes

Configure the DXP nodes located in the same data center with the remote Elasticsearch cluster containing the leader indexes. Provide the proper Elasticsearch Configuration values (via a `.config` file), then start the DXP nodes that will write to the leader indexes, to make sure they're reading and writing properly.

### Configure the Nodes that Reside with the Follower Indexes

Any DXP nodes that are going to read from local follower Elasticsearch indexes and write to remote leader indexes must have the CCR module deployed and configured (use a `.config` file).

Start the nodes and install the LES app.

## Make the Running Elasticsearch Clusters Know Their Respective Roles

From the local cluster containing the follower indexes, set the leader and perform the initial index replication. This involves calling two Elasticsearch APIs:

- Call the `/_cluster/settings` Elasticsearch API to define the cluster that acts as the remote cluster containing leader indexes.

## Replicate the Leader Indexes

- Call the `[indexName]/_ccr/follow` Elasticsearch API to configure the follower indexes on the appropriate cluster.
