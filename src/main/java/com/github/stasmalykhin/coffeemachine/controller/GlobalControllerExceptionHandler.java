package com.github.stasmalykhin.coffeemachine.controller;

import com.github.stasmalykhin.coffeemachine.exception.CoffeeMachineNotFoundException;
import com.github.stasmalykhin.coffeemachine.exception.CoffeeRecipeAlreadyExistsException;
import com.github.stasmalykhin.coffeemachine.exception.CoffeeRecipeNotCreatedException;
import com.github.stasmalykhin.coffeemachine.exception.CoffeeRecipeNotFoundException;
import com.github.stasmalykhin.coffeemachine.util.CustomResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Обрабатывает ошибки в контроллерах
 *
 * @author Stanislav Malykhin
 */
@Hidden
@Log4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(CoffeeMachineNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<CustomResponse> handlerException(CoffeeMachineNotFoundException e) {
        String message = "Coffee machine with id is wasn't found!";
        log.error(message, e);
        return new ResponseEntity<>(new CustomResponse(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CoffeeRecipeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<CustomResponse> handlerException(CoffeeRecipeNotFoundException e) {
        String message = "Coffee recipe with name is wasn't found!";
        log.error(message, e);
        return new ResponseEntity<>(new CustomResponse(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CoffeeRecipeNotCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<CustomResponse> handlerException(CoffeeRecipeNotCreatedException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CoffeeRecipeAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    private ResponseEntity<CustomResponse> handlerException(CoffeeRecipeAlreadyExistsException e) {
        String message = "Coffee recipe with name already exists!";
        log.error(message, e);
        return new ResponseEntity<>(new CustomResponse(message), HttpStatus.CONFLICT);
    }
}
