package com.mnadeem.graphql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GraphQLConfiguration {
	
	@Bean
	RestTemplate resTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	GraphQLTemplate graphQLTemplate() {
		return new GraphQLTemplate();
	}

}
