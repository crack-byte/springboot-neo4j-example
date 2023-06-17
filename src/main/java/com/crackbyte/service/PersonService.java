package com.crackbyte.service;

import com.crackbyte.model.Person;
import com.crackbyte.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public Mono<Person> createPerson(Person Person) {
        return personRepository.save(Person);
    }

    public Flux<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Mono<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public Mono<Person> findByName(String name) {
        return personRepository.findByName(name);
    }
}
