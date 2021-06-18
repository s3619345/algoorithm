package com.sense;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;


@SpringBootApplication(scanBasePackages = {"com.sense.newots.*"})
@EnableJms
@MapperScan("com.sense.newots.object.dao")
public class NewotsApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewotsApplication.class, args);
    }
}


