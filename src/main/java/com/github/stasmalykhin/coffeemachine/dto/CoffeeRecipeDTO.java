package com.github.stasmalykhin.coffeemachine.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Stanislav Malykhin
 */
@Getter
@Setter
public class CoffeeRecipeDTO {

    public String name;
    public int water;
    public int milk;
    public int beans;
    public int cups;
}
