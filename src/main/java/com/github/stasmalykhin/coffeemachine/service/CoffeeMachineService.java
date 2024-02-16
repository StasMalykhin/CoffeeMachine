package com.github.stasmalykhin.coffeemachine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stasmalykhin.coffeemachine.entity.CoffeeMachine;
import com.github.stasmalykhin.coffeemachine.entity.CoffeeRecipe;
import com.github.stasmalykhin.coffeemachine.exception.CoffeeMachineNotFoundException;
import com.github.stasmalykhin.coffeemachine.repository.CoffeeMachineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
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
    private final ObjectMapper objectMapper;
    @Value("#{${default-coffee-machine.ingredientsMap}}")
    private Map<String, Integer> ingredientsCoffeeMachine;


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

    public boolean useIngredientsToMakeCoffee(CoffeeMachine coffeeMachine, CoffeeRecipe recipe) {
        boolean enoughIngredients =
                coffeeMachine.checkForIngredients(recipe.water, recipe.milk, recipe.beans, recipe.cups);
        if (enoughIngredients) {
            coffeeMachine.useIngredients(recipe.water, recipe.milk, recipe.beans, recipe.cups);
            saveCoffeeMachine(coffeeMachine);
        }
        return enoughIngredients;
    }

    public boolean checkFullnessOfDatabase() {
        return coffeeMachineRepository.count() == 0;
    }

    public void fillDatabaseWithDefaultData() {
        log.info("Parameters of coffee machine have been added to BD");
        CoffeeMachine coffeeMachine = objectMapper.convertValue(ingredientsCoffeeMachine, CoffeeMachine.class);
        coffeeMachineRepository.save(coffeeMachine);
    }
}
