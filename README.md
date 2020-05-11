# sql-migration-library

This application uses **db per tenant model** and Spring's *AbstractRoutingDataSource* to choose the appropriate 
database connection for a given tenant. Assuming the tenant info is passed as part of the URL path like 
`http://localhost:8080/{tenantId}/api/student`, the *TenancyContextFilter* first parses the tenantId and sets the 
context in a threadlocal variable. This tenantId is being used as look-up key by Spring's *RoutingDatasource* to 
direct the jdbc calls to appropriate datasource. This application also uses Flyway to do the necessary migrations at 
application startup.
 
Note - 

1. We can **intercept jdbcTemplate calls** by using a *net.ttddyy.dsproxy.support.ProxyDataSource*  which provides 
several callbacks and also to add custom query transformer.

2. We can **intercept hibernate calls** by using custom class extending *org.hibernate.EmptyInterceptor* and 
overriding appropriate methods. We can register our interceptor by adding the following jpa property 
`hibernate.session_factory.interceptor`
