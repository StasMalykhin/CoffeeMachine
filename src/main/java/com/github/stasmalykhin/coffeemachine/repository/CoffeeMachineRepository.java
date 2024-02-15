package com.github.stasmalykhin.coffeemachine.repository;

import com.github.stasmalykhin.coffeemachine.entity.CoffeeMachine;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Stanislav Malykhin
 */
public interface CoffeeMachineRepository extends JpaRepository<CoffeeMachine, Integer> {
}
