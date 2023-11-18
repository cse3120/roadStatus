package com.api.road.status.configuration;

import com.api.road.status.deserializer.RoadResponseDeserializer;
import com.api.road.status.response.RoadResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RoadStatusConfiguration {
    @Value("${roadapi.app.key}")
    private String appKey;

    @Value("${roadapi.app.url}")
    private String appURL;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public String getAppKey() {
        return appKey;
    }

    public String getAppURL() {
        return appURL;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(RoadResponse.class, new RoadResponseDeserializer());
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    @Bean
    public OpenAPI myOpenAPI() {
        Info info = new Info()
                .title("RoadStatus API")
                .version("1.0")
                .description("This API exposes endpoints to view Road Status.");
        return new OpenAPI().info(info);
    }

}
