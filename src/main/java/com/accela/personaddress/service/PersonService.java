package com.accela.personaddress.service;

import com.accela.personaddress.domain.Person;
import com.accela.personaddress.dto.PersonDto;
import org.springframework.data.domain.Page;



public interface PersonService {

    Page<PersonDto> findAll(int pageNumber, int pageSize);

    PersonDto findPersonById(int id);

    Person getPersonDB(int id);

    long countPeople();

    void deletePersonById(int id);

    void save(PersonDto personDto);

    void update(PersonDto personDto);
}
