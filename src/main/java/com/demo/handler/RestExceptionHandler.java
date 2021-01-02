package com.demo.handler;

import com.demo.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Path.Node;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler(value
            = { ConstraintViolationException.class })
    protected ResponseEntity<Object> handleConflict(
            ConstraintViolationException ex, WebRequest request) {
        Map<String,String> map= new HashMap<String,String>();

        // I only need the first violation
        ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();
        // get the last node of the violation
        String field =null;
        String message= violation.getMessage();
        System.out.println(message);
        for (Node node : violation.getPropertyPath()) {
            field = node.getName();
        }
        map.put(field,message);

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(value
            = { ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Request failed";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }
}
