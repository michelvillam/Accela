package com.accela.personaddress.controller;

import com.accela.personaddress.dto.PersonDto;
import com.accela.personaddress.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

@RestController
@Validated
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person")
    public Page<PersonDto> getAllPeople(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") @PositiveOrZero Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") @PositiveOrZero Integer pageSize) {

        return personService.findAll(pageNumber, pageSize);
    }

    @GetMapping("person/{id}")
    public PersonDto getPersonById(@PathVariable("id") int personId)  {

        return personService.findPersonById(personId);
    }

    @GetMapping("/person/count")
    public long getNumberOfPeople(){

        return personService.countPeople();
    }

    @PostMapping("/person")
    public void signUpPerson(@RequestBody @Valid PersonDto personDto){

        personService.save(personDto);
    }

    @PutMapping("/person")
    public void updatePerson(@RequestBody @Valid PersonDto personDto){

        personService.update(personDto);
    }

    @DeleteMapping("/person/{id}")
    public void deletePersonById(@PathVariable("id") int personId){

        personService.deletePersonById(personId);
    }




}
