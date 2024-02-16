package com.github.stasmalykhin.coffeemachine.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @author Stanislav Malykhin
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coffee_machine")
public class CoffeeMachine extends BaseEntity {

    private int water;
    private int milk;
    private int beans;
    private int cups;
    private boolean isOn = false;

    public void turnOn() {
        this.isOn = true;
    }

    public void turnOff() {
        this.isOn = false;
    }

    public void addIngredients(int inputWater, int inputMilk,
                               int inputBeans, int inputCups) {
        setWater(getWater() + inputWater);
        setMilk(getMilk() + inputMilk);
        setBeans(getBeans() + inputBeans);
        setCups(getCups() + inputCups);
    }

    public void useIngredients(int outputWater, int outputMilk,
                               int outputBeans, int outputCups) {
        setWater(getWater() - outputWater);
        setMilk(getMilk() - outputMilk);
        setBeans(getBeans() - outputBeans);
        setCups(getCups() - outputCups);
    }

    public boolean checkForIngredients(int outputWater, int outputMilk,
                                       int outputBeans, int outputCups) {
        return getWater() - outputWater > 0 && getMilk() - outputMilk > 0 &&
                getBeans() - outputBeans > 0 && getCups() - outputCups > 0;
    }
}
