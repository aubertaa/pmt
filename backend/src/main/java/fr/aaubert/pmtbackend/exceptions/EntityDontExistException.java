package fr.aaubert.pmtbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class EntityDontExistException extends RuntimeException{
    public EntityDontExistException(String projectNotFound) {
    }
    public EntityDontExistException() {
    }
}