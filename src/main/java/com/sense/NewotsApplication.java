package com.sense;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.jms.annotation.EnableJms;


@SpringBootApplication(scanBasePackages = {"com.sense.newots.*"})
@EnableJms
@MapperScan("com.sense.newots.object.dao")
//@EnableAutoConfiguration()
public class NewotsApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(NewotsApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(NewotsApplication.class);
    }
}


