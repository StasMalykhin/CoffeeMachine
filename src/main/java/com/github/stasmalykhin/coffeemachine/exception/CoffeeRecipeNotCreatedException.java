package com.github.stasmalykhin.coffeemachine.exception;

/**
 * @author Stanislav Malykhin
 */
public class CoffeeRecipeNotCreatedException extends RuntimeException {

    public CoffeeRecipeNotCreatedException(String msg) {
        super(msg);
    }
}
