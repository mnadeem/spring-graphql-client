package com.mnadeem.graphql;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

import com.mnadeem.graphql.model.Person;

@SpringBootApplication
public class SpringGraphqlClientApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringGraphqlClientApplication.class);

	@Autowired
	private GraphQLTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(SpringGraphqlClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mutation();
		queryPersons();
		mutationVariable();
		
		renamingFields();
	}

	private void renamingFields() throws IOException {
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("male", "M");
		vars.put("female", "F");

		ResponseEntity<Map> res = template.postForResource("graphql/renaming_results.graphql", vars,  Map.class);
		LOGGER.info("Renaming fields : {} ", res);
	}

	private void mutationVariable() throws IOException {
		Person person = new Person();
		person.setName("Test Name");
		person.setDob(new Date());
		person.setGender("M");
		
		ResponseEntity<Map> res = template.postForResource("graphql/insert_person_mutation_variables.graphql", person,  Map.class);
		LOGGER.info("MutationVariable : {} ", res);
	}

	private void queryPersons() throws IOException {
		ResponseEntity<Map> res = template.postForResource("graphql/persons.graphql", Map.class);
		LOGGER.info("queryPersons : {} ", res);
	}

	private void mutation() throws IOException {
		ResponseEntity<Map> res = template.postForResource("graphql/insert_person_mutation.graphql", Map.class);
		LOGGER.info("Mutation : {} ", res);
	}
}
