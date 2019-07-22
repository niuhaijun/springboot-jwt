package com.niu.springbootjwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan({"com.niu.springbootjwt.mapper"})
@EnableTransactionManagement
public class SpringbootJwtApplication {

  public static void main(String[] args) {

    SpringApplication.run(SpringbootJwtApplication.class, args);
  }

}
