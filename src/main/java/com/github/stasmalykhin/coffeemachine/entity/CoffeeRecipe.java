package com.github.stasmalykhin.coffeemachine.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * @author Stanislav Malykhin
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coffee_recipe")
public class CoffeeRecipe extends BaseEntity {


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
}
