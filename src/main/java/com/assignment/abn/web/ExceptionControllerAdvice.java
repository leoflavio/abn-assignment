package com.assignment.abn.web;

import com.assignment.abn.exception.RecipeNotFoundException;
import com.assignment.abn.exception.RecipeSaveCommandException;
import com.assignment.abn.exception.RecipeUpdateCommandException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@ControllerAdvice
@RestController
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { RecipeNotFoundException.class })
    protected ResponseEntity<Object> handleNotFoundExceptions(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Recipe not Found!!";
        log.error(bodyOfResponse, ex);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { RecipeUpdateCommandException.class, RecipeSaveCommandException.class })
    protected ResponseEntity<Object> handleDepartmentCommandException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "An error occurred during an operation";
        log.error(bodyOfResponse, ex);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "An invalid value was provided";
        log.error(bodyOfResponse, ex);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
