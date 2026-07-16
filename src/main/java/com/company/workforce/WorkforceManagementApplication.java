package com.company.workforce;

import com.company.workforce.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(
        JwtProperties.class
)
public class WorkforceManagementApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WorkforceManagementApplication.class);
        app.setApplicationStartup(new BufferingApplicationStartup(2048));
        app.run(args);
    }

}