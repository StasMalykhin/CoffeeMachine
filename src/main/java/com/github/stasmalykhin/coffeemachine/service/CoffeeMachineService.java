package com.github.stasmalykhin.coffeemachine.service;

import com.github.stasmalykhin.coffeemachine.entity.CoffeeMachine;
import com.github.stasmalykhin.coffeemachine.exception.CoffeeMachineNotFoundException;
import com.github.stasmalykhin.coffeemachine.repository.CoffeeMachineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * Методы для работы с кофемашинами в БД.
 *
 * @author Stanislav Malykhin
 */
@Service
@Log4j
@RequiredArgsConstructor
public class CoffeeMachineService {
    private final CoffeeMachineRepository coffeeMachineRepository;
    private final CoffeeRecipeService coffeeRecipeService;
    @Value("${coffee-machine.water}")
    private int water;
    @Value("${coffee-machine.milk}")
    private int milk;
    @Value("${coffee-machine.beans}")
    private int beans;
    @Value("${coffee-machine.cups}")
    private int cups;

    public CoffeeMachine findCoffeeMachineById(int id) {
        Optional<CoffeeMachine> foundCoffeeMachine = coffeeMachineRepository.findById(id);
        return foundCoffeeMachine.orElseThrow(CoffeeMachineNotFoundException::new);
    }

    public void saveCoffeeMachine(CoffeeMachine coffeeMachine) {
        coffeeMachineRepository.save(coffeeMachine);
    }

    public void addIngredientsToDB(CoffeeMachine coffeeMachine, int inputWater, int inputMilk,
                                   int inputBeans, int inputCups) {
        coffeeMachine.addIngredients(inputWater, inputMilk, inputBeans, inputCups);
        saveCoffeeMachine(coffeeMachine);
    }

    public boolean useIngredientsToMakeCoffee(CoffeeMachine coffeeMachine, String nameCoffeeRecipe) {
        int water = coffeeRecipeService.getWaterByName(nameCoffeeRecipe);
        int milk = coffeeRecipeService.getMilkByName(nameCoffeeRecipe);
        int beans = coffeeRecipeService.getBeansByName(nameCoffeeRecipe);
        int cups = coffeeRecipeService.getCupsByName(nameCoffeeRecipe);
        boolean enoughIngredients =
                coffeeMachine.checkForIngredients(water, milk, beans, cups);
        if (enoughIngredients) {
            coffeeMachine.useIngredients(water, milk, beans, cups);
            saveCoffeeMachine(coffeeMachine);
        }
        return enoughIngredients;
    }

    public boolean checkFullnessOfDatabase() {
        return coffeeMachineRepository.count() == 0;
    }

    public void fillDatabase() {
        log.info("Parameters of coffee machine have been added to BD");
        coffeeMachineRepository.save(CoffeeMachine.builder()
                .water(water)
                .milk(milk)
                .beans(beans)
                .cups(cups)
                .build());
    }
}
