package com.crackbyte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;

@SpringBootApplication
@EnableReactiveNeo4jRepositories
public class SpringbootNeo4jExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootNeo4jExampleApplication.class, args);
	}

}
