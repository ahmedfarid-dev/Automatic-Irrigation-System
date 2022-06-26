package com.bankmisr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bankmisr.common.exception.ResourceNotFoundException;


@ControllerAdvice
public class ResourceExceptionController {
   @ExceptionHandler(value = ResourceNotFoundException.class)
   public ResponseEntity<Object> exception(ResourceNotFoundException exception) {
      return new ResponseEntity<>("Resource not found "+ exception.getMessage(), HttpStatus.NOT_FOUND);
   }
}
