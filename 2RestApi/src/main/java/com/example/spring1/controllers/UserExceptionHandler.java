package com.example.spring1.controllers;

import com.example.spring1.exceptions.helper.JsonResponse;
import com.example.spring1.exceptions.UserNotFoundException;
import com.example.spring1.exceptions.UserPutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

public class UserExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<JsonResponse> handleError2(HttpServletRequest req, UserNotFoundException ex) {
        return new ResponseEntity<>(new JsonResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserPutException.class)
    public ResponseEntity<JsonResponse> handleError1(HttpServletRequest req, UserPutException ex) {
        return new ResponseEntity<>(new JsonResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
