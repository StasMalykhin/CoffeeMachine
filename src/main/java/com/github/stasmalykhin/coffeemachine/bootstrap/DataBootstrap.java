package com.github.stasmalykhin.coffeemachine.bootstrap;

import com.github.stasmalykhin.coffeemachine.service.CoffeeMachineService;
import com.github.stasmalykhin.coffeemachine.service.CoffeeRecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Загружает при запуске приложения характеристики одной кофемашины и
 * несколько дефолтных рецептов кофе в БД.
 *
 * @author Stanislav Malykhin
 */
@Log4j
@Component
@RequiredArgsConstructor
public class DataBootstrap implements ApplicationRunner {

    private final CoffeeRecipeService coffeeRecipeService;
    private final CoffeeMachineService coffeeMachineService;

    public void run(ApplicationArguments args) {
        boolean databaseWithAirlinesIsNotFull = coffeeMachineService.checkFullnessOfDatabase();
        if (databaseWithAirlinesIsNotFull) {
            try {
                coffeeMachineService.fillDatabase();
            } catch (Exception e) {
                log.error(String.format("Cannot fill database with coffee machines. Error=%s", e.getMessage()));
            }
        }
        boolean databaseWithCoffeeRecipesIsNotFull = coffeeRecipeService.checkFullnessOfDatabase();
        if (databaseWithCoffeeRecipesIsNotFull) {
            try {
                coffeeRecipeService.fillDatabase();
            } catch (Exception e) {
                log.error(String.format("Cannot fill database with coffee recipes. Error=%s", e.getMessage()));
            }
        }


    }
}
