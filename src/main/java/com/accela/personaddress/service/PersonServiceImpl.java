package com.accela.personaddress.service;

import com.accela.personaddress.domain.Person;
import com.accela.personaddress.dto.PersonDto;
import com.accela.personaddress.exception.PersonNotFoundException;
import com.accela.personaddress.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;
    private Mapper mapper;

    public PersonServiceImpl(PersonRepository personRepository, Mapper mapper) {
        this.personRepository = personRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<PersonDto> findAll(int pageNumber, int pageSize) {

        log.debug("Getting all the people with pageNumber: {}, and pageSize: {}",pageNumber, pageSize);

        Pageable pageable = createPageable(pageNumber, pageSize);
        return personRepository.findAll(pageable).map(person -> mapper.map(person, PersonDto.class));
    }

    @Override
    public PersonDto findPersonById(int id) {

        log.debug("Finding person with id: {}", id);

        PersonDto personDto = personRepository.findById(id)
                .map(person -> mapper.map(person, PersonDto.class))
                .orElseThrow(() -> {
                    log.error("Person not found with id: {}", id);
                    throw new PersonNotFoundException();
                });

        log.debug("Retrieved person: {} ", personDto);

        return personDto;
    }

    @Override
    public long countPeople() {

        log.debug("Getting number of people.");
        return personRepository.count();
    }


    @Override
    public void deletePersonById(int id) {

        log.info("Deleting person with id: {}", id);
        Person personDB = getPersonDB(id);
        personRepository.delete(personDB);
    }

    @Override
    public void save(PersonDto personDto) {

        log.info("Saving person: {}", personDto);
        Person person = mapper.map(personDto, Person.class);
        personRepository.save(person);
    }

    @Override
    public void update(PersonDto personDto) {

        log.info("Updating person with id: {}", personDto.getId());

        Person personDB = getPersonDB(personDto.getId());

        personDB.setFirstName(personDto.getFirstName());
        personDB.setLastName(personDto.getLastName());

        personRepository.save(personDB);

    }

    @Override
    public Person getPersonDB(int id) {

        return personRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Person not found with id: {}", id);
                        throw new PersonNotFoundException("Person not found with id: " + id);
                    });
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize){

        Sort sortByName = Sort.by(Sort.Direction.DESC, "lastName");
        return PageRequest.of(pageNumber, pageSize, sortByName);
    }
}
