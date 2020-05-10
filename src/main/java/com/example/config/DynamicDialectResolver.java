package com.example.config;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;

public class DynamicDialectResolver implements DialectResolver {
  @Override
  public Dialect resolveDialect(DialectResolutionInfo info) {
    return null;
  }
}
