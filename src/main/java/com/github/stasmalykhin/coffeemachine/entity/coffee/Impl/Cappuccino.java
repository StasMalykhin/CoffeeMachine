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
public class Cappuccino extends AbstractCoffeeRecipe implements CoffeeRecipe {

    @Value("${cappuccino.name}")
    public String name;
    @Value("${cappuccino.water}")
    public int water;
    @Value("${cappuccino.milk}")
    public int milk;
    @Value("${cappuccino.beans}")
    public int beans;
    @Value("${cappuccino.cups}")
    public int cups;
}
