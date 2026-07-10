package com.company.workforce;

import com.company.workforce.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(
        JwtProperties.class
)
public class WorkforceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                WorkforceManagementApplication.class,
                args
        );
    }


}