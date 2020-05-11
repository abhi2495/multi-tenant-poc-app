package com.example.config;

import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import net.ttddyy.dsproxy.transform.TransformInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "tenants")
public class TenantConfigProperties {

  private Map<Object, Object> datasources = new LinkedHashMap<>();

  public Map<Object, Object> getDatasources() {
    return datasources;
  }

  public void setDatasources(Map<String, Map<String, String>> datasources) {
    datasources
        .forEach((key, value) -> this.datasources.put(key, convert(value)));

  }

  private DataSource convert(Map<String, String> source) {
    DataSource dataSource = DataSourceBuilder.create()
        .url(source.get("url"))
        .driverClassName(source.get("driver"))
        .username(source.get("username"))
        .password(source.get("password"))
        .build();
    return ProxyDataSourceBuilder.create(dataSource).queryTransformer((TransformInfo transformInfo) -> {
      String query = transformInfo.getQuery();
      //We can transform the query here
      return query;
    }).build();
  }

}
