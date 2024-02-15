package com.github.stasmalykhin.coffeemachine.entity.coffee.Impl;

import com.github.stasmalykhin.coffeemachine.entity.coffee.AbstractCoffeeRecipe;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
/**
 * Класс для создания новых кофейных рецептов.
 *
 * @author Stanislav Malykhin
 */
@Entity
public class NewCoffeeRecipe extends AbstractCoffeeRecipe {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    public String name;
    @Min(value = 0, message = "Age should be greater than 0")
    public int water;
    @Min(value = 0, message = "Age should be greater than 0")
    public int milk;
    @Min(value = 0, message = "Age should be greater than 0")
    public int beans;
    @Min(value = 0, message = "Age should be greater than 0")
    public int cups;

    public NewCoffeeRecipe() {

    }
}
