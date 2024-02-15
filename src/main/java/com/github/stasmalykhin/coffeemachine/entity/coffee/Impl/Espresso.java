package com.github.stasmalykhin.coffeemachine.entity.coffee.Impl;

import com.github.stasmalykhin.coffeemachine.entity.coffee.AbstractCoffeeRecipe;
import com.github.stasmalykhin.coffeemachine.entity.coffee.CoffeeRecipe;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Дефолтный кофейный рецепт.
 *
 * @author Stanislav Malykhin
 */
@Entity
@Component
public class Espresso extends AbstractCoffeeRecipe implements CoffeeRecipe {

    @Value("${espresso.name}")
    public String name;
    @Value("${espresso.water}")
    public int water;
    @Value("${espresso.milk}")
    public int milk;
    @Value("${espresso.beans}")
    public int beans;
    @Value("${espresso.cups}")
    public int cups;
}