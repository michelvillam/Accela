package com.accela.personaddress.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found")
public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException() {
        super();
    }


    public PersonNotFoundException(String message) {
        super(message);
    }

}
