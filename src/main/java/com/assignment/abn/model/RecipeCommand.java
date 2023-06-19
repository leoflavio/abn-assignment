package com.assignment.abn.model;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public record RecipeCommand(
        @Schema(description = "Recipe ID")
        @JsonView(Views.Update.class)
        Long id,
        @Schema(type = "string", description = "Recipe name")
        String name,
        @Schema(description = "The number of serving")
        int numServing,
        @Schema(type = "string", description = "Instruction how to prepare the recipe")
        String instructions,
        @Schema(description = "The recipe is vegetarian: 'true' or 'false'")
        boolean vegetarian,
        @Schema(description = "The recipe ingredients list")
        Set<String> ingredients
) {}
