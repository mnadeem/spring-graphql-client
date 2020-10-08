package com.mnadeem.graphql;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GraphQLTemplate {

	@Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    
    @Value("${app.graphql.api.url:/graphql}")
    private String graphqlMapping;

    private String loadQuery(String location) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + location);
        return loadResource(resource);
    }

    private String loadResource(Resource resource) throws IOException {
        try (InputStream inputStream = resource.getInputStream()) {
            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        }
    }

    private String createJsonQuery(String graphql, ObjectNode variables)
            throws JsonProcessingException {

        ObjectNode wrapper = objectMapper.createObjectNode();
        wrapper.put("query", graphql);
        wrapper.set("variables", variables);
        return objectMapper.writeValueAsString(wrapper);
    }

    public <T> ResponseEntity<T> postForResource(String graphqlResource, ObjectNode variables, Class<T> responseType) throws IOException {
        String graphql = loadQuery(graphqlResource);
        String payload = createJsonQuery(graphql, variables);
        return post(payload, responseType);
    }

    public <T> ResponseEntity<T> postForResource(String graphqlResource, Class<T> responseType) throws IOException {
        return postForResource(graphqlResource, null, responseType);
    }

    private <T> ResponseEntity<T> post(String payload, HttpHeaders headers, Class<T> responseType) {
        return postForEntity(forJson(payload, headers), responseType);
    }

    private <T> ResponseEntity<T> post(String payload, Class<T> responseType) {
        return post(payload, new HttpHeaders(), responseType);
    }

    private <T> ResponseEntity<T> postForEntity(HttpEntity<Object> request, Class<T> responseType) {
    	return restTemplate.postForEntity(graphqlMapping, request, responseType);
    }

    private static HttpEntity<Object> forJson(String json, HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(json, headers);
    }
}
