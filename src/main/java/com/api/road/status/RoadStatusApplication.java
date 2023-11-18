package com.api.road.status;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class RoadStatusApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoadStatusApplication.class, args);
    }

}
