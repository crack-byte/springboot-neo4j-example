package com.crackbyte.repository;


import com.crackbyte.model.Person;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PersonRepository extends ReactiveNeo4jRepository<Person, Long> {

    Mono<Person> findByName(String name);

    Flux<Person> findByTeammatesName(String name);
}