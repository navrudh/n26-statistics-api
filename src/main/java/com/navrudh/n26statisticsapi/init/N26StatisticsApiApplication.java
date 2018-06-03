package com.navrudh.n26statisticsapi.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@ComponentScan(basePackages = "com.navrudh.n26statisticsapi")
@SpringBootApplication
@EnableScheduling
public class N26StatisticsApiApplication {

    @PostConstruct
    private void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(N26StatisticsApiApplication.class, args);
    }
}
