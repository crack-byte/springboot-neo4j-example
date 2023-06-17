package com.crackbyte;

import com.crackbyte.model.Person;
import com.crackbyte.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class Bootstrap {
    private final PersonRepository personRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void run() {
        personRepository.deleteAll().block();
        Person greg = new Person("Greg");
        Person roy = new Person("Roy");
        Person craig = new Person("Craig");

        List<Person> team = Arrays.asList(greg, roy, craig);

        log.info("Before linking up with Neo4j...");

        team.forEach(person -> log.info("\t" + person.toString()));

        personRepository.save(greg).block();
        personRepository.save(roy).block();
        personRepository.save(craig).block();

        Mono<Person> gregMono = personRepository.findByName(greg.getName());
        gregMono.flatMap(gregPerson -> {
            gregPerson.worksWith(roy);
            gregPerson.worksWith(craig);
            return personRepository.save(gregPerson);
        }).block();

        Mono<Person> royMono = personRepository.findByName(roy.getName());
        royMono.flatMap(royPerson -> {
            royPerson.worksWith(craig);
            return personRepository.save(royPerson);
        }).block();

        log.info("Lookup each person by name...");
        team.forEach(person -> personRepository.findByName(person.getName())
                .doOnNext(foundPerson -> log.info("\t" + foundPerson.toString()))
                .block());

        Flux<Person> teammatesFlux = personRepository.findByTeammatesName(greg.getName());
        teammatesFlux.collectList().doOnNext(teammates -> {
            log.info("The following have Greg as a teammate...");
            teammates.forEach(person -> log.info("\t" + person.getName()));
        }).block();
        log.info("--------------------------");
    }
}
