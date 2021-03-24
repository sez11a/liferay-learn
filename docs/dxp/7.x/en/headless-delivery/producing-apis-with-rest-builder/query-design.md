# Query Design

Once you have an API set up, you may need to add some complexity to it. For collection queries in particular, there are several options for increasing the functionality of your API to provide more convenient access to the relevant data. This includes pagination, search, filtering, and sorting.

To add any of these options to your API, make the relevant edits to your OpenAPI profile (YAML or JSON file), regenerate with `./gradlew buildREST`, and fill out the necessary implementation in the appropriate `*ResourceImpl` class.

## Pagination

To add pagination to your endpoints, add `page` and `pageSize` as query parameters to your OpenAPI profile, like this:

```yaml
- in: query
  name: page
  schema:
      type: integer
- in: query
  name: pageSize
  schema:
      type: integer
```

Those two parameters add a `Pagination pagination` parameter in a `get[Entity]Page` method signature to restrict the number of entries returned in the `Page.of` constructor.

Pagination is highly recommended for entities that can have many elements, to avoid very large requests.

## Search Operations

Adding support for filtering, sorting, and searching is trickier. The first step is to add the query parameters to the OpenAPI profile, like this:

```yaml
- in: query
  name: filter
  schema:
      type: string
- in: query
  name: search
  schema:
      type: string
- in: query
  name: sort
  schema:
      type: string
```

The `get[Entity]` or `get[Entity]Page` method signature then receives a Sort object, a Filter object, and string with the search request. Those objects use [Liferay's indexing framework](../../using-search/developer-guide/model-entity-indexing.rst). This gives you many benefits, like support for permissions out-of-the-box and having to write very little code to achieve sort, filter, and search.

So first, make sure your entity is indexed using the indexing framework.

Once that's done you have three things to do:

1. Add an `EntityModel` implementation to translate between Liferay's indexing framework and your code
1. Inject your `entityModel` into your resource implementation.
1. Call Search utilities to avoid boilerplate code.

### Add an EntityModel Implementation

The `EntityModel` implementation is a simple class that translates between how your entity's properties are referenced in your API versus the search index.

```java
public class EntityEntityModel implements EntityModel {

	public EntityEntityModel() {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new StringEntityField(
				"name", locale -> Field.getSortableFieldName(Field.NAME))
		);
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}
```

The `EntityModel` decouples the way you filter/sort from the way you index the information. You could use one field to sort, backed internally by several indexed fields, or vice-versa.

### Inject Your EntityModel

Injecting your `EntityModel` is easy, your resource implementation just has to implement the `EntityModelResource` interface.

A simple entity model, like the example given, doesn't have any dynamic fields, so you can instantiate it directly and return it in the `getEntityModel` method, like this:

```java
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/entity.properties",
	scope = ServiceScope.PROTOTYPE, service = EntityResource.class
)
public class EntityResourceImpl extends BaseEntityResourceImpl implements
	EntityModelResource {

	...

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityEntityModel;
	}

	private EntityModel _entityEntityModel = new EntityEntityModel();

}
```

### Call search utilities

Finally, call `SearchUtil.search()` to link everything together. It requires these parameters:

1. `actions`: a map of actions the user can take on the returned elements (see [Available Actions](./available-actions.md) for more details).

1. `booleanQueryUnsafeConsumer`: a boolean query to restrict the information we want to retrieve.

1. `filter`: pass-through of the filter object.

1. `indexerClass`: the class of the entity that to filter/search.

1. `keywords`: pass-through of the search string.

1. `pagination`: pass-through of the pagination object (to read the row requested).

1. `queryConfigUnsafeConsumer`: the configuration for the fields that you want to return, typically the id to do a query later, in the `transformUnsafeFunction`.

1. `searchContextUnsafeConsumer`: global configuration of the query.

1. `sorts`: pass-through of the sorts object.

1. `transformUnsafeFunction`: the function that transforms from `Document` (of the indexing framework) to your entity, either searching in the database, your persistence, another API, etc.

The code would be similar to this:

```java
@Override
public Page<Entity> getEntitiesPage(
		String search, Filter filter, Pagination pagination, Sort[] sorts)
	throws Exception {

	return SearchUtil.search(
		Collections.emptyMap(),
		booleanQuery -> {},
		filter, Entity.class, search, pagination,
		queryConfig -> queryConfig.setSelectedFieldNames(
			Field.ENTRY_CLASS_PK),
		searchContext -> searchContext.setCompanyId(
			contextCompany.getCompanyId()),
		sorts,
		document -> new Entity(document.get("id"))); // your implementation here
}
```

### Using Your filter, search, and sort

Liferay uses OData to express our filter queries, following this [syntax](https://help.liferay.com/hc/en-us/articles/360036343152-Filter-Sort-and-Search).

And that's it! Now you can filter/search and sort by the fields you defined in your `EntityModel`.