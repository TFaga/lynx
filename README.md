# Lynx
[![Build Status](https://img.shields.io/travis/TFaga/lynx/master.svg?style=flat)](https://travis-ci.org/TFaga/lynx)

> Lynx automatically exposes your JPA entities as a REST service with build-in metadata and query support using JAX-RS.

## Usage

You can download the binaries from the [releases page](https://github.com/TFaga/lynx/releases).

The project is split into two modules `core` and `rest`. The former contains all the main utilities, classes and
helpers that use JPA to manipulate the data. The latter module contains the generic JAX-RS classes that use the core
module to automatically expose your entities as a REST services. If you only want the utilities that are used behind
the scenes and don't want to expose your entities as a REST service (or you want to do it on your own) only include
the `core` module. However if you want to use everything then only include the `rest` module.

You can enable the Lynx by adding the following dependency to your project:

```xml
<dependency>
    <groupId>com.github.tfaga.lynx</groupId>
    <artifactId>lynx-core</artifactId>
    <version>${lynx.version}</version>
</dependency>
```

## Getting started

### JAX-RS

When implementing REST services the URI context information is needed. The URI can be obtained by adding `UriInfo` context to selected Resource:

```java
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("customers")
public class CustomerResource {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private CustomerService customerBean;
}
```

Using the URI context information the query parameters can be constructed by using the `QueryParameters` class:

```java
@GET
public Response getAllCustomers() {
    QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
    List<Customer> customers = customerBean.getCustomers(query);
    return Response.ok(customers).build();
}
```

### CDI implementation

After parsing the query parameters they can be used to query or count entities using the `JPAUtils` class:


```java
@RequestScoped
public class CustomerService {

    @PersistenceContext
    private EntityManager em;

    public List<Customer> getCustomers(QueryParameters query) {
        List<Customer> customers = JPAUtils.queryEntities(em, Customer.class, query);
        return customers;
    }


    public Long getCustomerCount(QueryParameters query) {
        Long count = JPAUtils.queryEntitiesCount(em, Customer.class, query);
        return count;
    }
}
```
We can also build the query using `QueryStringDefaults` class which applies the following defaults (if not specified by the client):

- max results: maximum number of entities that can be returned
- limit: default number of entities returned
- offset: default offset

```java
@Context
private UriInfo uriInfo;

@Inject
private QueryStringDefaults qsd;

@Inject
private EntityManager em;

@GET
public Response getList() {
    QueryParameters query = qsd.builder().queryEncoded(uriInfo.getRequestUri().getRawQuery()).build();

    List<Customer> allCustomers = JPAUtils.queryEntities(em, Customer.class, query);
    Long allCustomersCount = JPAUtils.queryEntitiesCount(em, Customer.class, query);

    return Response.ok(allCustomers).header("X-Total-Count", allCustomersCount).build();
}
```

Defaults can either be constructed:

```java
private QueryStringDefaults qsd = new QueryStringDefaults().maxLimit(100).defaultLimit(20).defaultOffset(0);
```

or injected with CDI and a producer class:

```java
public class RestProducer {

    @Produces
    @ApplicationScoped
    public QueryStringDefaults getQueryStringDefaults() {
        return new QueryStringDefaults()
                .maxLimit(100)
                .defaultLimit(20)
                .defaultOffset(0);
    }
}
```

### Examples

After the implementation of Rest resources and CDI beans, the query parameters can be used for pagination, sorting and filtering of JPA entities.

#### Pagination

The offset parameter indicates the position of the first entity which should be returned and the limit parameter indicates the number of entities.

```
GET /v1/customers?offset=10
GET /v1/customers?limit=5
GET /v1/customers?offset=10&limit=5
```

We must also return the number of all entities so the client can display correct number of page buttons. One way of doing this is with a custom HTTP header.

```java
...
QueryParameters query = qsd.builder().queryEncoded(uriInfo.getRequestUri().getRawQuery()).build();

List<Customer> allCustomers = JPAUtils.queryEntities(em, Customer.class, query);
Long allCustomersCount = JPAUtils.queryEntitiesCount(em, Customer.class, query);

return Response.ok(allCustomers).header("X-Total-Count", allCustomersCount).build();
```

#### Sorting

Sorting of entities can be specified by providing the field and direction.

```
GET v1/customers?order=id DESC
GET v1/customers?order=lastName ASC
```

We can chain several sorts together.

```
GET v1/customers?order=email ASC,lastname DESC
```

After the last user specified sort, order by unique ID is automatically appended at the end of the query for deterministic sorting of same-valued columns.

#### Filtering

The entities can be filtered by using multiple operations:

* EQ | Equals
* EQIC | Equals ignore case
* NEQ | Not equal
* NEQIC | Not equal ignoring case
* LIKE | Pattern matching (% replaces characters, _ replaces a single character)
* LIKEIC | Pattern matching ignore case (% replaces characters, _ replaces a single character)
* GT | Greater than
* GTE | Greater than or equal
* LT | Lower than
* LTE | Lower than or equal
* IN | In set
* INIC | In set ignore case
* NIN | Not in set
* NINIC | Not in set ignore case
* ISNULL | Null
* ISNOTNULL | Not null

```
GET v1/customers?filter=id:EQ:1
GET v1/customers?filter=lastName:NEQIC:'doe'
GET v1/customers?filter=lastName:LIKE:H%
GET v1/customers?filter=age:GT:10
GET v1/customers?filter=id:IN:[1,2,3]
GET v1/customers?filter=lastName:ISNULL
GET v1/customers?filter=lastName:ISNOTNULL

GET v1/customers?filter=age:GT:10 id:IN:[1,2,3] lastName:ISNOTNULL
```

There are some special cases:
- If we want to use `LIKE` filter and query values that include a percent sign, it needs to be URL encoded (%25).
- Dates must be in ISO-8601 format, `+` sign must be URL encoded (%2B). Single quotes for value are required.

```
GET v1/customers?where=firstName:LIKE:'%somestring%25doe'
GET v1/customers?where=createdAt:GT:'2017-06-12T11:57:00%2B00:00'
```

#### Partial responses
We can select which fields we want returned in the resulting JSON with the `fields` parameter.

```
GET v1/customers?fields=firstName,lastName
```

#### Traversing OneToMany and ManyToOne relations
We can traverse entity attributes similar to JPQL style. Let's say each customer has many `cars` and we want to find owners of specific brand:

```
GET v1/cu
stomers?filter=cars.brand:EQ:bmw
```
It also works in reverse:

```
GET v1/cars?filter=customer.firstName:EQ:John
```

This would find all `Cars` that have an owner named `John`.

#### Combine pagination, sorting and filtering

Pagination, sorting and filtering of entities can be combined by separating them with &.

```
GET /v1/customers?offset=10&limit=5&order=id DESC&filter=age:GT:10 id:IN:[1,2,3] lastName:ISNOTNULL
```

Filters are chained together with `AND` operator. `OR WHERE` clause is not supported at this time. 

#### Additional criteria query manipulation
Predicate constructed from query parameters can be further changed. For example:

```java
List<Customer> allCustomers = JPAUtils.queryEntities(em, Customer.class,
      (p, cb, r) -> cb.and(p, cb.equal(r.get("firstName"), "John")));
```

Where:
- `p` is existing Predicate
- `cb` is CriteriaBuilder and
- `c` is Root 

With this, programmer has the full power of Criteria API to further manipulate the query.

## Building

Ensure you have JDK 8 (or newer), Maven 3.2.1 (or newer) and Git installed

```bash
    java -version
    mvn -version
    git --version
```

First clone the Lynx repository:

```bash
    git clone https://github.com/TFaga/lynx.git
    cd lynx
```
    
To build Lynx run:

```bash
    mvn install
```

This will build all modules and run the testsuite. 
    
Once completed you will find the build archives in the modules respected `target` folder.

## Changelog

Recent changes can be viewed on Github on the [Releases Page](https://github.com/TFaga/lynx/releases)

## Contribute

See the [contributing docs](https://github.com/TFaga/lynx/blob/master/CONTRIBUTING.md)

When submitting an issue, please follow the [guidelines](https://github.com/TFaga/lynx/blob/master/CONTRIBUTING.md#bugs).

When submitting a bugfix, write a test that exposes the bug and fails before applying your fix. Submit the test alongside the fix.

When submitting a new feature, add tests that cover the feature.

## License

MIT