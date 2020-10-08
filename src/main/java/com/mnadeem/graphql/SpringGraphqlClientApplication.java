package com.mnadeem.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringGraphqlClientApplication implements CommandLineRunner {
	
	@Autowired
	private GraphQLTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(SpringGraphqlClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(template);		
	}
}
