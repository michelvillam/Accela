package com.accela.personaddress.service;

import com.accela.personaddress.domain.Person;
import com.accela.personaddress.dto.PersonDto;
import com.accela.personaddress.exception.PersonNotFoundException;
import com.accela.personaddress.repository.PersonRepository;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PersonServiceTest {

    @InjectMocks
    private  PersonServiceImpl personService;

    @Mock
    private PersonRepository personRepository;

    @Spy
    private DozerBeanMapper mapper;

    @Captor
    private ArgumentCaptor<Person> personArgumentCaptor;

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final String DEFAULT_SORT_PAGE = "lastName";
    private static final int PERSON_ID = 5;
    private static final long NUMBER_OF_PEOPLE = 3L;


    //**************** FIND ALL PEOPLE ********************************************

    @Test
    public void getAllPeopleShouldReturnData(){

        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, Sort.by(Sort.Direction.DESC, DEFAULT_SORT_PAGE));

        when(personRepository.findAll(pageRequest)).thenReturn(getPageablePersonList());

        Page<PersonDto> personDtos = personService.findAll(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);

        assertEquals(2, personDtos.getTotalElements());

    }

    @Test
    public void getAllPeopleShouldReturnEmptyData(){

        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, Sort.by(Sort.Direction.DESC, DEFAULT_SORT_PAGE));

        when(personRepository.findAll(pageRequest)).thenReturn(getEmptyPageablePersonList());

        Page<PersonDto> personDtos = personService.findAll(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);

        assertEquals(0, personDtos.getTotalElements() );
    }

    //**************** FIND ONE PERSON ********************************************

    @Test
    public void getPersonByIdShouldReturnPerson(){

        when(personRepository.findById(PERSON_ID)).thenReturn(Optional.of(createPerson("Jose", "Gomez", PERSON_ID)));

        PersonDto personDto = personService.findPersonById(PERSON_ID);

        assertEquals(PERSON_ID, personDto.getId());
        assertEquals("Jose", personDto.getFirstName());
        assertEquals("Gomez", personDto.getLastName());
    }

    @Test
    public void getPersonByIdShouldReturnNotFound(){

        when(personRepository.findById(PERSON_ID)).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, ()-> personService.findPersonById(PERSON_ID));
    }


    //**************** COUNT PEOPLE ********************************************
    @Test
    public void countPeopleShouldReturnNumberOfPeople(){

        when(personRepository.count()).thenReturn(NUMBER_OF_PEOPLE);

        long numberOfPeople = personService.countPeople();

        assertEquals(NUMBER_OF_PEOPLE, numberOfPeople);
    }

    //**************** SAVE PERSON ********************************************
    @Test
    public void signUpPersonShouldSavePerson(){

        PersonDto personDto = PersonDto.builder()
                .id(PERSON_ID)
                .firstName("Pedro")
                .lastName("Jimenez")
                .build();

        personService.save(personDto);

        verify(personRepository, times(1)).save(personArgumentCaptor.capture());

        assertEquals(PERSON_ID, personArgumentCaptor.getValue().getId());
        assertEquals("Pedro", personArgumentCaptor.getValue().getFirstName());
        assertEquals("Jimenez", personArgumentCaptor.getValue().getLastName());
    }


    //**************** UPDATE PERSON ********************************************

    @Test
    public void updatePersonShouldModifyPerson(){
        PersonDto personDto = PersonDto.builder()
                .id(PERSON_ID)
                .firstName("Pepe")
                .lastName("Jimenez")
                .build();

        when(personRepository.findById(PERSON_ID))
                .thenReturn(Optional.of(createPerson("Paco", "Rodriguez", PERSON_ID)));

        personService.update(personDto);

        verify(personRepository, times(1)).save(personArgumentCaptor.capture());

        assertEquals(PERSON_ID, personArgumentCaptor.getValue().getId());
        assertEquals("Pepe", personArgumentCaptor.getValue().getFirstName());
        assertEquals("Jimenez", personArgumentCaptor.getValue().getLastName());
    }

    @Test
    public void updatePersonShouldReturnNotFound(){
        PersonDto personDto = PersonDto.builder()
                .id(PERSON_ID)
                .firstName("Pepe")
                .lastName("Jimenez")
                .build();

        when(personRepository.findById(PERSON_ID))
                .thenThrow(PersonNotFoundException.class);

        assertThrows(PersonNotFoundException.class, ()-> personService.update(personDto));
    }


    //**************** DELETE PERSON ********************************************
    @Test
    public void deletePersonShouldDeletePerson(){

        Person person = createPerson("Paco", "Rodriguez", PERSON_ID);
        when(personRepository.findById(PERSON_ID))
                .thenReturn(Optional.of(person));

        personService.deletePersonById(PERSON_ID);

        verify(personRepository, times(1)).delete(personArgumentCaptor.capture());

        assertEquals(person.getId(), personArgumentCaptor.getValue().getId());
        assertEquals(person.getFirstName(), personArgumentCaptor.getValue().getFirstName());
        assertEquals(person.getLastName(), personArgumentCaptor.getValue().getLastName());
    }

    @Test
    public void deletePersonShouldReturnPersonNotFound(){

        Person person = createPerson("Paco", "Rodriguez", PERSON_ID);
        when(personRepository.findById(PERSON_ID))
                .thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, ()-> personService.deletePersonById(PERSON_ID));
    }




    private Page<Person> getPageablePersonList(){

        List<Person> persons = new ArrayList<>();
        persons.add(createPerson("Pepe", "Rodriguez", 2));
        persons.add(createPerson("Pedro", "Martin", 3));

        return new PageImpl<>(persons);
    }

    private Person createPerson(String firstName, String lastName, int id) {
        return Person.builder().firstName(firstName).lastName(lastName).id(id).build();
    }

    private Page<Person> getEmptyPageablePersonList(){
        return new PageImpl<>(new ArrayList<>());
    }

}
