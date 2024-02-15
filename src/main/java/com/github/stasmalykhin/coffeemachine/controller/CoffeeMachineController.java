package com.github.stasmalykhin.coffeemachine.controller;

import com.github.stasmalykhin.coffeemachine.dto.CoffeeMachineDTO;
import com.github.stasmalykhin.coffeemachine.entity.CoffeeMachine;
import com.github.stasmalykhin.coffeemachine.service.CoffeeMachineService;
import com.github.stasmalykhin.coffeemachine.service.CoffeeRecipeService;
import com.github.stasmalykhin.coffeemachine.util.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Обрабатывает запросы, связанные с кофемашиной
 *
 * @author Stanislav Malykhin
 */
@Tag(
        name = "Кофемашины",
        description = "Все методы для работы с кофемашинами"
)
@RestController
@RequestMapping("/api/coffee-machine")
@RequiredArgsConstructor
public class CoffeeMachineController {

    private final CoffeeRecipeService coffeeRecipeService;
    private final CoffeeMachineService coffeeMachineService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Включить выбранную по id кофемашину")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Кофемашина включена",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Кофемашина с таким id не найдена",
                    content = {@Content(
                            schema = @Schema(implementation = CustomResponse.class))})})
    @GetMapping("{id}/turn-on")
    public ResponseEntity<CustomResponse> start(
            @Parameter(description = "id кофемашины")
            @PathVariable int id) {
        CoffeeMachine coffeeMachine = coffeeMachineService.findCoffeeMachineById(id);
        String message;
        if (!coffeeMachine.isOn()) {
            coffeeMachine.turnOn();
            coffeeMachineService.saveCoffeeMachine(coffeeMachine);
            message = "Кофемашина включена";
            return new ResponseEntity<>(new CustomResponse(message), HttpStatus.OK);
        }
        message = "Кофемашина уже была включена";
        return new ResponseEntity<>(new CustomResponse(message), HttpStatus.OK);
    }

    @Operation(summary = "Выключить выбранную по id кофемашину")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Кофемашина выключена",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Кофемашина с таким id не найдена",
                    content = {@Content(
                            schema = @Schema(implementation = CustomResponse.class))})})
    @GetMapping("{id}/turn-off")
    public ResponseEntity<CustomResponse> stop(
            @Parameter(description = "id кофемашины")
            @PathVariable int id) {
        CoffeeMachine coffeeMachine = coffeeMachineService.findCoffeeMachineById(id);
        String message;
        if (coffeeMachine.isOn()) {
            coffeeMachine.turnOff();
            coffeeMachineService.saveCoffeeMachine(coffeeMachine);
            message = "Кофемашина выключена";
            return new ResponseEntity<>(new CustomResponse(message), HttpStatus.OK);
        }
        message = "Кофемашина уже была выключена";
        return new ResponseEntity<>(new CustomResponse(message), HttpStatus.OK);
    }

    @Operation(summary = "Получить список ингредиентов у выбранной по id кофемашины")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Получен список ингредиентов у выбранной кофемашины",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CoffeeMachineDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Кофемашина с таким id не найдена",
                    content = {@Content(
                            schema = @Schema(implementation = CustomResponse.class))})})
    @GetMapping("/{id}/ingredients")
    public CoffeeMachineDTO showLeftoverIngredientsAtCoffeeMachine(
            @Parameter(description = "id кофемашины")
            @PathVariable int id) {
        CoffeeMachine coffeeMachine = coffeeMachineService.findCoffeeMachineById(id);
        return convertToCoffeeMachineDTO(coffeeMachine);
    }

    @Operation(summary = "Пополнить количество ингредиентов у выбранной по id кофемашины")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Получен список ингредиентов у выбранной кофемашины после пополнения",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CoffeeMachineDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Количество ингредиентов, введенных пользователем, не прошло валидацию",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Кофемашина с таким id не найдена",
                    content = {@Content(
                            schema = @Schema(implementation = CustomResponse.class))})})
    @GetMapping("{id}/ingredients/add")
    public CoffeeMachineDTO replenishIngredients(
            @Parameter(description = "id кофемашины")
            @PathVariable int id,
            @Parameter(description = "количество воды, которое хотим добавить в кофемашину")
            @RequestParam(defaultValue = "0", value = "water")
            @Min(value = 0, message = "Age should be greater than 0") int inputWater,
            @Parameter(description = "количество молока, которое хотим добавить в кофемашину")
            @RequestParam(defaultValue = "0", value = "milk")
            @Min(value = 0, message = "Age should be greater than 0") int inputMilk,
            @Parameter(description = "количество кофейных зерен, которое хотим добавить в кофемашину")
            @RequestParam(defaultValue = "0", value = "beans")
            @Min(value = 0, message = "Age should be greater than 0") int inputBeans,
            @Parameter(description = "количество стаканчиков, которое хотим добавить в кофемашину")
            @RequestParam(defaultValue = "0", value = "cups")
            @Min(value = 0, message = "Age should be greater than 0") int inputCups) {
        CoffeeMachine coffeeMachine = coffeeMachineService.findCoffeeMachineById(id);
        coffeeMachineService.addIngredientsToDB(coffeeMachine, inputWater, inputMilk, inputBeans, inputCups);
        return convertToCoffeeMachineDTO(coffeeMachine);
    }

    @Operation(summary = "Сделать кофейный напиток, предварительно выбрав " +
            "по id кофемашину и по названию кофеный рецепт")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Сделан кофе по выбранному рецепту, если пройдена проверка на то, " +
                            "что кофемашина включена и ингредиентов в ней достаточно",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Не введено название кофейного рецепта",
                    content = {@Content(
                            schema = @Schema(implementation = CustomResponse.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Кофемашина с таким id или кофейный рецепт с таким названием не найдены",
                    content = {@Content(
                            schema = @Schema(implementation = CustomResponse.class))})})
    @GetMapping("/{id}/make-coffee")
    public ResponseEntity<CustomResponse> makeCoffee(
            @Parameter(description = "id кофемашины")
            @PathVariable int id,
            @Parameter(description = "название кофейнного рецепта")
            @RequestParam(value = "name", required = false) String inputNameRecipe) {
        CoffeeMachine coffeeMachine = coffeeMachineService.findCoffeeMachineById(id);
        String message;
        if (coffeeMachine.isOn()) {
            boolean nameOfRecipeHasBeenEntered = inputNameRecipe != null;
            if (nameOfRecipeHasBeenEntered) {
                coffeeRecipeService.checkForCoffeeRecipeNotFound(inputNameRecipe);
                boolean coffeeIsDone =
                        coffeeMachineService.useIngredientsToMakeCoffee(coffeeMachine, inputNameRecipe);
                if (coffeeIsDone) {
                    message = "В кофемашине сварили кофе " + inputNameRecipe;
                    return new ResponseEntity<>(new CustomResponse(message), HttpStatus.OK);
                }
                message = "В кофемашине недостаточно ингредиентов для приготовления кофе";
                return new ResponseEntity<>(new CustomResponse(message), HttpStatus.OK);
            }
            message = "Не введено название кофейного рецепта";
            return new ResponseEntity<>(new CustomResponse(message), HttpStatus.BAD_REQUEST);
        }
        message = "Кофемашина выключена";
        return new ResponseEntity<>(new CustomResponse(message), HttpStatus.OK);
    }

    private CoffeeMachineDTO convertToCoffeeMachineDTO(CoffeeMachine coffeeMachine) {
        return modelMapper.map(coffeeMachine, CoffeeMachineDTO.class);
    }
}
