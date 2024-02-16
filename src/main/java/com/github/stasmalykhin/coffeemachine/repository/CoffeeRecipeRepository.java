package com.github.stasmalykhin.coffeemachine.repository;

import com.github.stasmalykhin.coffeemachine.entity.CoffeeRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoffeeRecipeRepository extends JpaRepository<CoffeeRecipe, Integer> {
    Optional<CoffeeRecipe> findCoffeeRecipeByName(String name);
}
