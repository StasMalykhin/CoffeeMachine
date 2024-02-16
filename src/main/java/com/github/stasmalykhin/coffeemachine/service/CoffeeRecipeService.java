package com.github.stasmalykhin.coffeemachine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stasmalykhin.coffeemachine.entity.CoffeeRecipe;
import com.github.stasmalykhin.coffeemachine.exception.CoffeeRecipeAlreadyExistsException;
import com.github.stasmalykhin.coffeemachine.exception.CoffeeRecipeNotFoundException;
import com.github.stasmalykhin.coffeemachine.repository.CoffeeRecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
    private final CoffeeRecipeRepository coffeeRecipeRepository;
    private final ObjectMapper objectMapper;

    @Value("${espresso.name}")
    private String nameEspresso;
    @Value("#{${espresso.ingredientsMap}}")
    private Map<String, Integer> ingredientsEspresso;

    @Value("${latte.name}")
    private String nameLatte;
    @Value("#{${latte.ingredientsMap}}")
    private Map<String, Integer> ingredientsLatte;

    @Value("${cappuccino.name}")
    private String nameCappuccino;
    @Value("#{${cappuccino.ingredientsMap}}")
    private Map<String, Integer> ingredientsCappuccino;


    public List<CoffeeRecipe> findAllCoffeeRecipes() {
        return coffeeRecipeRepository.findAll();
    }

    public void saveCoffeeRecipe(CoffeeRecipe coffeeRecipe) {
        coffeeRecipeRepository.save(coffeeRecipe);
    }

    public void deleteCoffeeRecipe(CoffeeRecipe coffeeRecipe) {
        coffeeRecipeRepository.delete(coffeeRecipe);
    }

    public void checkCoffeeRecipeAlreadyExists(String name) {
        Optional<CoffeeRecipe> foundCoffeeRecipe =
                coffeeRecipeRepository.findCoffeeRecipeByName(name);
        if (foundCoffeeRecipe.isPresent()) {
            throw new CoffeeRecipeAlreadyExistsException();
        }
    }

    public CoffeeRecipe findCoffeeRecipeByName(String name) {
        Optional<CoffeeRecipe> foundCoffeeRecipe =
                coffeeRecipeRepository.findCoffeeRecipeByName(name);
        return foundCoffeeRecipe.orElseThrow(CoffeeRecipeNotFoundException::new);
    }

    public boolean checkFullnessOfDatabase() {
        return coffeeRecipeRepository.count() == 0;
    }

    public void fillDatabaseWithDefaultData() {
        log.info("All default coffee recipes have been added to BD");

        CoffeeRecipe espresso = objectMapper.convertValue(ingredientsEspresso, CoffeeRecipe.class);
        espresso.setName(nameEspresso);
        CoffeeRecipe latte = objectMapper.convertValue(ingredientsLatte, CoffeeRecipe.class);
        latte.setName(nameLatte);
        CoffeeRecipe cappuccino = objectMapper.convertValue(ingredientsCappuccino, CoffeeRecipe.class);
        cappuccino.setName(nameCappuccino);

        coffeeRecipeRepository.save(espresso);
        coffeeRecipeRepository.save(latte);
        coffeeRecipeRepository.save(cappuccino);
    }
}
