package com.example.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
public class JavaReadWriteSplit {
	
	public static void main(String[] args) {

	 // 配置真实数据源
    Map<String, DataSource> dataSourceMap = new HashMap<>();
    
    // 配置主库
    BasicDataSource masterDataSource = new BasicDataSource();
    masterDataSource.setDriverClassName("com.mysql.jdbc.Driver");
    masterDataSource.setUrl("jdbc:mysql://localhost:3306/ds_master");
    masterDataSource.setUsername("root");
    masterDataSource.setPassword("");
    dataSourceMap.put("ds_master", masterDataSource);
    
    // 配置第一个从库
    BasicDataSource slaveDataSource1 = new BasicDataSource();
    slaveDataSource1.setDriverClassName("com.mysql.jdbc.Driver");
    slaveDataSource1.setUrl("jdbc:mysql://localhost:3306/ds_slave0");
    slaveDataSource1.setUsername("root");
    slaveDataSource1.setPassword("");
    dataSourceMap.put("ds_slave0", slaveDataSource1);
    
    // 配置第二个从库
    BasicDataSource slaveDataSource2 = new BasicDataSource();
    slaveDataSource2.setDriverClassName("com.mysql.jdbc.Driver");
    slaveDataSource2.setUrl("jdbc:mysql://localhost:3306/ds_slave1");
    slaveDataSource2.setUsername("root");
    slaveDataSource2.setPassword("");
    dataSourceMap.put("ds_slave1", slaveDataSource2);
    
	}
}
