package com.assignment.abn.mapper;

import com.assignment.abn.model.Recipe;
import com.assignment.abn.model.RecipeCommand;
import com.assignment.abn.model.RecipeQuery;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

@Mapper
public interface RecipeMapper {
    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    Recipe RecipeCommandToRecipe(RecipeCommand recipeCommand);
    RecipeQuery RecipeToRecipeQuery(Recipe recipe);

    default void updateRecipe(Recipe recipe, RecipeCommand recipeCommand){
        Objects.requireNonNull(recipe);
        recipe.setName(recipeCommand.name());
        recipe.setNumServing(recipeCommand.numServing());
        recipe.setInstructions(recipeCommand.instructions());
        recipe.setVegetarian(recipeCommand.vegetarian());
        recipe.setIngredients(recipeCommand.ingredients());
    }
}
