package com.lwaddress;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * @author zhangwq
  * @date 2018/6/30 20:58
  */
 public class addressTest1 {

 @Test
 public static void main(String[] args){

//获取连接

  DruidDataSource dataSource = new DruidDataSource();

  dataSource.setUrl("jdbc:clickhouse://192.168.1.119:8123/lwaddr?useUnicode=true&characterEncoding=utf-8");

  dataSource.setDriverClassName("ru.yandex.clickhouse.ClickHouseDriver");

  dataSource.setUsername("");

  dataSource.setPassword("");

  dataSource.setMaxActive(20);

  dataSource.setInitialSize(5);


  try{

   DruidPooledConnection conn = dataSource.getConnection();

   PreparedStatement pst = conn.prepareStatement("show tables");

   ResultSet rs = pst.executeQuery();

   while(rs.next()){

    System.out.println(rs.getInt("id")+":"+rs.getString("rightName"));

   }

  } catch (SQLException e) {
   e.printStackTrace();
  }


 }
 }
