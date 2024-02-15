package com.github.stasmalykhin.coffeemachine.controller;

import com.github.stasmalykhin.coffeemachine.entity.coffee.AbstractCoffeeRecipe;
import com.github.stasmalykhin.coffeemachine.entity.coffee.Impl.NewCoffeeRecipe;
import com.github.stasmalykhin.coffeemachine.exception.CoffeeRecipeNotCreatedException;
import com.github.stasmalykhin.coffeemachine.service.CoffeeRecipeService;
import com.github.stasmalykhin.coffeemachine.util.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Обрабатывает запросы, связанные с кофейными рецептами
 *
 * @author Stanislav Malykhin
 */
@Tag(
        name = "Кофейные рецепты",
        description = "Все методы для работы с кофейными рецептами"
)
@RestController
@RequestMapping("/api/coffee-recipes")
@RequiredArgsConstructor
public class CoffeeRecipeController {

    private final CoffeeRecipeService coffeeRecipeService;

    @Operation(summary = "Получить список всех кофейных рецептов")
    @ApiResponse(responseCode = "200", description = "Получен список всех кофейных рецептов",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = AbstractCoffeeRecipe.class)))})
    @GetMapping
    public List<AbstractCoffeeRecipe> showAllCoffeeDrinks() {
        return coffeeRecipeService.findAllCoffeeRecipes();
    }

    @Operation(summary = "Удалить выбранный по названию кофейный рецепт")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Удален выбранный по названию кофейный рецепт",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HttpStatus.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Кофейный рецепт с таким названием не найден",
                    content = {@Content(
                            schema = @Schema(implementation = CustomResponse.class))})})
    @DeleteMapping
    @Transactional
    public ResponseEntity<HttpStatus> removeCoffeeRecipe(
            @Parameter(description = "название кофейнного рецепта")
            @RequestParam(value = "name", required = false) String inputNameRecipe) {
        coffeeRecipeService.checkForCoffeeRecipeNotFound(inputNameRecipe);
        coffeeRecipeService.deleteCoffeeRecipe(inputNameRecipe);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Создать новый кофейный рецепт")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Создан новый кофейный рецепт",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HttpStatus.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Входящий JSON не прошел валидацию",
                    content = {@Content(
                            schema = @Schema(implementation = CustomResponse.class))}),
            @ApiResponse(responseCode = "409",
                    description = "Кофейный рецепт с таким названием уже есть",
                    content = {@Content(
                            schema = @Schema(implementation = CustomResponse.class))})})
    @PostMapping("/new")
    public ResponseEntity<HttpStatus> createNewCoffeeRecipe(@RequestBody @Valid NewCoffeeRecipe newCoffeeRecipe,
                                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors)
                errorMsg.append(error.getField()).append(" - ").
                        append(error.getDefaultMessage()).append("; ");

            throw new CoffeeRecipeNotCreatedException(errorMsg.toString());
        }
        coffeeRecipeService.checkCoffeeRecipeAlreadyExists(newCoffeeRecipe.name);
        coffeeRecipeService.saveCoffeeRecipe(newCoffeeRecipe);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
