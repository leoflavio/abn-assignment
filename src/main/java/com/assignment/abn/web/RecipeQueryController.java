package com.assignment.abn.web;

import com.assignment.abn.model.RecipeQuery;
import com.assignment.abn.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RecipeQueryController {

    private final RecipeService recipeService;

    @Operation(summary = "Get recipe by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Recipe", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RecipeQuery.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Recipe not found", content = @Content)})
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RecipeQuery> getRecipeById(@Parameter(description = "Recipe ID to be retrieved", example = "123") @PathVariable("id") Long id){
        log.info("Get recipe by id: " + id);
        var recipe = recipeService.getRecipeById(id);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @Operation(summary = "List of recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe retrieved"),
            @ApiResponse(responseCode = "500", description = "Error when trying to list the recipe")
    })
    @GetMapping(value = "/list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<RecipeQuery>> getAllRecipe(){
        log.info("List of Recipes");
        var list = recipeService.listAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "Searching for recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe retrieved"),
            @ApiResponse(responseCode = "500", description = "Error when trying to search for recipe")
    })
    @GetMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<RecipeQuery>> searchRecipe(
            @Parameter(description = "Recipe with the name", example = "Chocolate cake")
            @RequestParam Optional<String> name,
            @Parameter(description = "The number of servings", example = "4")
            @RequestParam Optional<Integer> numServing,
            @Parameter(description = "Instructions", example = "Oven 30 min")
            @RequestParam Optional<String> instructions,
            @Parameter(description = "The dish is vegetarian (true/false)")
            @RequestParam Optional<Boolean> vegetarian,
            @Parameter(description = "Specific ingredients included")
            @RequestParam Optional<Set<String>> ingredientsIn,
            @Parameter(description = "Specific ingredients excluded")
            @RequestParam Optional<Set<String>> ingredientsOut
    ){
        log.info("Search of Recipes");
        var list = recipeService.searchRecipe(name, numServing, instructions, vegetarian, ingredientsIn, ingredientsOut);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
