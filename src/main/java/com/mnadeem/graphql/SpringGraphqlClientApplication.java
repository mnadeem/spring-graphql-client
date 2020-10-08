package com.mnadeem.graphql;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
public class SpringGraphqlClientApplication implements CommandLineRunner {

	@Autowired
	private GraphQLTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(SpringGraphqlClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ResponseEntity<Map> res = template.postForResource("graphql/persons.graphql", Map.class);
		System.out.println(res);		
	}
}
