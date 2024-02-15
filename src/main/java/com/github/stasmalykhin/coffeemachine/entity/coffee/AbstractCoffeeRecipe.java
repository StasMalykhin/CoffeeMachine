package com.github.stasmalykhin.coffeemachine.entity.coffee;

import jakarta.persistence.*;

/**
 * @author Stanislav Malykhin
 */
@Entity
@Table(name = "coffee_recipe")
public abstract class AbstractCoffeeRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public String name;
    public int water;
    public int milk;
    public int beans;
    public int cups;
}
