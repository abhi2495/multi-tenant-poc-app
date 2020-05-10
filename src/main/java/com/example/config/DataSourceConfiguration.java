package com.example.config;

import com.example.tenancy.TenancyContextHolder;
import com.example.tenancy.Tenant;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(TenantConfigProperties.class)
public class DataSourceConfiguration {

  private final TenantConfigProperties tenantConfigProperties;

  public DataSourceConfiguration(TenantConfigProperties tenantConfigProperties) {
    this.tenantConfigProperties = tenantConfigProperties;
  }

  @Bean
  public DataSource dataSource() {
    AbstractRoutingDataSource customDataSource = new AbstractRoutingDataSource() {
      @Override
      protected Object determineCurrentLookupKey() {
        Tenant tenant = TenancyContextHolder.getContext().getTenant();
        if (tenant != null) {
          return tenant.getId();
        }
        return Tenant.DEFAULT_TENANT_ID;
      }
    };
    customDataSource.setTargetDataSources(tenantConfigProperties.getDatasources());
    return customDataSource;
  }

}
