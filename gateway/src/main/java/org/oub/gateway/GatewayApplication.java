package org.oub.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	// Static Method

	//@Bean
	/*
	RouteLocator routeLocator(RouteLocatorBuilder builder){
		return builder.routes()
				.route((r)->r.path("/customers/**").uri("http://localhost:8081/"))
				.route((r)->r.path("/products/**").uri("http://localhost:8082/"))
				.build();
	}
	 */

	// With Eureka Discovery

	//@Bean
	RouteLocator routeLocator(RouteLocatorBuilder builder){
		// With Load Balancer
		return builder.routes()
				.route((r)->r.path("/customers/**").uri("lb://CUSTOMER-SERVICE"))
				.route((r)->r.path("/products/**").uri("lb://PRODUCT-SERVICE"))
				.build();
	}

	// Dynamic Method

	@Bean
	DiscoveryClientRouteDefinitionLocator definitionLocator(
			ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties properties){
		return new DiscoveryClientRouteDefinitionLocator(rdc, properties);
	}
}
