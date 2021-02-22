package com.accela.personaddress.repository;

import com.accela.personaddress.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Integer> {

    /**
     * Method to get all people.
     *
     * @param pageable page information
     * @return page of list of people
     */
    Page<Person> findAll(Pageable pageable);

    /**
     * Method to get a person by identity.
     *
     * @param id the person identity
     * @return an optional object of person
     */
    Optional<Person> findById(int id);



}
