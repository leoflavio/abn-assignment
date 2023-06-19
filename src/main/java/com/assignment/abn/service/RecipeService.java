package com.assignment.abn.service;

import com.assignment.abn.model.RecipeCommand;
import com.assignment.abn.model.RecipeQuery;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RecipeService {

    Long save(RecipeCommand recipeCommand);
    Long update(RecipeCommand recipeCommand);
    void delete(Long id);
    List<RecipeQuery> listAll();
    RecipeQuery getRecipeById(Long id);
    List<RecipeQuery> searchRecipe(Optional<String> name,
                                   Optional<Integer> numServing,
                                   Optional<String> instructions,
                                   Optional<Boolean> vegetarian,
                                   Optional<Set<String>> ingredientsIn,
                                   Optional<Set<String>> ingredientsOut);
}
