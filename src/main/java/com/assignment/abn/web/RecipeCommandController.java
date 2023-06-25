package com.assignment.abn.web;

import com.assignment.abn.model.RecipeCommand;
import com.assignment.abn.model.Views;
import com.assignment.abn.service.RecipeService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
public class RecipeCommandController {

    private final RecipeService recipeService;

    @Operation(summary = "Create new Recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Error when trying to create recipe")
    })
    @JsonView(Views.Create.class)
    @PostMapping(value = "/recipes", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Long> saveRecipe(@RequestBody RecipeCommand recipeCommand){
        log.info("Calling saveRecipe");
        var recipeId = recipeService.save(recipeCommand);
        return new ResponseEntity<>(recipeId, HttpStatus.OK);
    }

    @Operation(summary = "Update Recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Recipe not found"),
            @ApiResponse(responseCode = "500", description = "Error when trying to create recipe")
    })
    @JsonView(Views.Update.class)
    @PutMapping(value = "/recipes", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Long> updateRecipe(@RequestBody RecipeCommand recipeCommand){
        log.info("Calling updateRecipe");
        var recipeId = recipeService.update(recipeCommand);
        return new ResponseEntity<>(recipeId, HttpStatus.OK);
    }

    @Operation(summary = "Delete Recipe by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Recipe not found"),
            @ApiResponse(responseCode = "500", description = "Error when trying to delete recipe")
    })
    @DeleteMapping("/recipes/{recipe-id}")
    public ResponseEntity<?> deleteRecipeById(@Parameter(description = "Recipe ID to be deleted", example = "123") @PathVariable("recipe-id") Long id){
        log.info("Calling deleteRecipeById: " + id);
        recipeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
