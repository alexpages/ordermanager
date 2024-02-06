package com.alexpages.ordermanager.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@EntityScan("com.alexpages.ordermanager.entity")
@ComponentScan("com.alexpages.ordermanager.service")
public class OrderManagerConfig {

	@Bean(name = "google")
    public RestTemplate googleRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        return new RestTemplate(requestFactory);
    }
}