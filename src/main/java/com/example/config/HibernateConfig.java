package com.example.config;

import org.hibernate.EmptyInterceptor;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class HibernateConfig {
  @Bean
  LocalContainerEntityManagerFactoryBean entityManagerFactory(
      DataSource dataSource, JpaProperties jpaProperties,
      JpaVendorAdapter jpaVendorAdapter) {

    Map<String, Object> jpaPropertiesMap = new HashMap<>(jpaProperties.getProperties());
    jpaPropertiesMap.put("hibernate.session_factory.interceptor", hibernateInterceptor());
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan("com.example*");
    em.setJpaVendorAdapter(jpaVendorAdapter);
    em.setJpaPropertyMap(jpaPropertiesMap);
    return em;
  }

  private EmptyInterceptor hibernateInterceptor() {
    return new EmptyInterceptor() {
      @Override
      public String onPrepareStatement(String sql) {
        String preparedStatement = super.onPrepareStatement(sql);
        //Do something
        return preparedStatement;
      }
    };
  }

}
