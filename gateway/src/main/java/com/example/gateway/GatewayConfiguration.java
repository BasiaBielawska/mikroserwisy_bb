package com.example.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .uri("http://httpbin.org:80"))
                .route(p -> p.path("/api/tasks/**")
                        .uri("lb://rest-http-controller-service-application"))
                .route(p -> p
                        .path("/post")
                        .uri("http://httpbin.org:80"))
                .route(p -> p.path("/api/tasks/**")
                        .uri("lb://rest-http-controller-service-application"))
                .route(p -> p
                        .path("/put")
                        .uri("http://httpbin.org:80"))
                .route(p -> p.path("/api/tasks/**")
                        .uri("lb://rest-http-controller-service-application"))
                .route(p -> p
                        .path("/delete")
                        .uri("http://httpbin.org:80"))
                .route(p -> p.path("/api/tasks/**")
                        .uri("lb://rest-http-controller-service-application"))

                .build();
    }
}
