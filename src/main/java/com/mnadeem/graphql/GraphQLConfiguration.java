package com.mnadeem.graphql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GraphQLConfiguration {

    @Value("${app.graphql.api.url:/graphql}")
    private String graphqlUrl;

    @Value("${app.graphql.api.key.name}")
    private String apiKeyName;

    @Value("${app.graphql.api.key.value}")
    private String apiKeyValue;

	@Bean
	RestTemplate resTemplate() {
		return new RestTemplate();
	}

	@Bean
	GraphQLTemplate graphQLTemplate() {
		return new GraphQLTemplate(graphqlUrl, apiKeyName, apiKeyValue);
	}

}
