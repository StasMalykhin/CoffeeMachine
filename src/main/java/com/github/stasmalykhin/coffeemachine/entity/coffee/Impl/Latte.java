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
public class Latte extends AbstractCoffeeRecipe implements CoffeeRecipe {

    @Value("${latte.name}")
    public String name;
    @Value("${latte.water}")
    public int water;
    @Value("${latte.milk}")
    public int milk;
    @Value("${latte.beans}")
    public int beans;
    @Value("${latte.cups}")
    public int cups;
}
