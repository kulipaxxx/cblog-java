package com.cheng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cheng.mapper")
public class CblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(CblogApplication.class, args);
    }

}
