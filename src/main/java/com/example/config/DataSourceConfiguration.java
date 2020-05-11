package com.example.config;

import com.example.tenancy.TenancyContextHolder;
import com.example.tenancy.Tenant;
import org.flywaydb.core.Flyway;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

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

  @PostConstruct
  private void migrate() {
    tenantConfigProperties.getDatasources().values()
        .stream()
        .map(dataSource -> (DataSource) dataSource)
        .forEach(this::migrate);
  }

  private void migrate(DataSource dataSource) {
    Flyway flyway = Flyway.configure().dataSource(dataSource).load();
    flyway.migrate();
  }

}
