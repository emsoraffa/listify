package com.solberg.persistence;

import javax.sql.DataSource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

public class DataHandler {

  private final DataSource dataSource;

  public DataHandler(DataSource dataSource) {
    this.dataSource = dataSource;

  }
}
