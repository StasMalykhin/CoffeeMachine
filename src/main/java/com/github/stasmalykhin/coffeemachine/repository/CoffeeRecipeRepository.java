package com.github.stasmalykhin.coffeemachine.repository;

import com.github.stasmalykhin.coffeemachine.entity.coffee.AbstractCoffeeRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Stanislav Malykhin
 */
public interface CoffeeRecipeRepository extends JpaRepository<AbstractCoffeeRecipe, Integer> {

    Optional<AbstractCoffeeRecipe> findAbstractCoffeeRecipeByName(String name);

    void deleteByName(String name);

    @Query("SELECT water FROM AbstractCoffeeRecipe WHERE name = :name")
    int getWaterByName(@Param("name") String name);

    @Query("SELECT milk FROM AbstractCoffeeRecipe WHERE name = :name")
    int getMilkByName(@Param("name") String name);

    @Query("SELECT beans FROM AbstractCoffeeRecipe WHERE name = :name")
    int getBeansByName(@Param("name") String name);

    @Query("SELECT cups FROM AbstractCoffeeRecipe WHERE name = :name")
    int getCupsByName(@Param("name") String name);
}
