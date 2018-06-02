package com.navrudh.n26statisticsapi.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.navrudh.n26statisticsapi")
@SpringBootApplication
public class N26StatisticsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(N26StatisticsApiApplication.class, args);
    }
}
