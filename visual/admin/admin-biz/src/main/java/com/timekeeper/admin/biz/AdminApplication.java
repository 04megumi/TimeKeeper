package com.timekeeper.admin.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@ComponentScan(value = "com.timekeeper.common.data.mybatis")
@ComponentScan(value = "com.timekeeper.common.security")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
