package com.github.stasmalykhin.coffeemachine.service;

import com.github.stasmalykhin.coffeemachine.entity.coffee.AbstractCoffeeRecipe;
import com.github.stasmalykhin.coffeemachine.entity.coffee.CoffeeRecipe;
import com.github.stasmalykhin.coffeemachine.exception.CoffeeRecipeAlreadyExistsException;
import com.github.stasmalykhin.coffeemachine.exception.CoffeeRecipeNotFoundException;
import com.github.stasmalykhin.coffeemachine.repository.CoffeeRecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Методы для работы с кофейными рецептами в БД.
 *
 * @author Stanislav Malykhin
 */
@Service
@Log4j
@RequiredArgsConstructor
public class CoffeeRecipeService {
    private final List<CoffeeRecipe> recipes;
    private final CoffeeRecipeRepository coffeeRecipeRepository;


    public int getWaterByName(String name) {
        return coffeeRecipeRepository.getWaterByName(name);
    }

    public int getMilkByName(String name) {
        return coffeeRecipeRepository.getMilkByName(name);
    }

    public int getBeansByName(String name) {
        return coffeeRecipeRepository.getBeansByName(name);
    }

    public int getCupsByName(String name) {
        return coffeeRecipeRepository.getCupsByName(name);
    }


    public List<AbstractCoffeeRecipe> findAllCoffeeRecipes() {
        return coffeeRecipeRepository.findAll();
    }

    public void saveCoffeeRecipe(AbstractCoffeeRecipe coffeeRecipe) {
        coffeeRecipeRepository.save(coffeeRecipe);
    }

    public void deleteCoffeeRecipe(String name) {
        coffeeRecipeRepository.deleteByName(name);
    }

    public void checkCoffeeRecipeAlreadyExists(String name) {
        Optional<AbstractCoffeeRecipe> foundCoffeeRecipe =
                coffeeRecipeRepository.findAbstractCoffeeRecipeByName(name);
        if (foundCoffeeRecipe.isPresent()) {
            throw new CoffeeRecipeAlreadyExistsException();
        }
    }

    public void checkForCoffeeRecipeNotFound(String name) {
        Optional<AbstractCoffeeRecipe> foundCoffeeRecipe =
                coffeeRecipeRepository.findAbstractCoffeeRecipeByName(name);
        if (foundCoffeeRecipe.isEmpty()) {
            throw new CoffeeRecipeNotFoundException();
        }
    }

    public boolean checkFullnessOfDatabase() {
        return coffeeRecipeRepository.count() == 0;
    }

    public void fillDatabase() {
        log.info("All default coffee recipes have been added to BD");
        recipes.stream().forEach(recipe -> coffeeRecipeRepository.save((AbstractCoffeeRecipe) recipe));
    }
}
