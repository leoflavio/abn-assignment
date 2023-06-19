package com.assignment.abn.service;

import com.assignment.abn.exception.RecipeNotFoundException;
import com.assignment.abn.exception.RecipeSaveCommandException;
import com.assignment.abn.exception.RecipeUpdateCommandException;
import com.assignment.abn.model.Recipe;
import com.assignment.abn.model.RecipeCommand;
import com.assignment.abn.model.RecipeQuery;
import com.assignment.abn.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Test
    void testSave_Success() {
        RecipeCommand recipeCommand = new RecipeCommand(null,"name", 3, "test", true, Set.of("test"));

        Recipe savedRecipe = Recipe.builder().id(1L).ingredients(Set.of("test")).build();
        when(recipeRepository.save(any(Recipe.class))).thenReturn(savedRecipe);

        Long savedRecipeId = recipeService.save(recipeCommand);

        assertEquals(1L, savedRecipeId);
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void testSave_ThrowsException() {
        RecipeCommand recipeCommand = new RecipeCommand(null,"name", 3, "test", true, Set.of("test"));

        when(recipeRepository.save(any(Recipe.class))).thenThrow(new RuntimeException("Error occurred during saving"));

        assertThrows(RecipeSaveCommandException.class, () -> recipeService.save(recipeCommand));
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void testUpdate_RecipeExists() {
        Long recipeId = 1L;
        RecipeCommand recipeCommand = new RecipeCommand(recipeId,"name", 3, "test", true, Set.of("test"));


        Recipe existingRecipe = Recipe.builder().id(recipeId).ingredients(Set.of("test")).build();
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(existingRecipe));

        Long updatedRecipeId = recipeService.update(recipeCommand);

        assertEquals(recipeId, updatedRecipeId);
        verify(recipeRepository, times(1)).findById(recipeId);
        verify(recipeRepository, times(1)).save(existingRecipe);
    }

    @Test
    public void testUpdate_RecipeNotFound() {
        Long recipeId = 1L;
        RecipeCommand recipeCommand = new RecipeCommand(recipeId,"name", 3, "test", true, Set.of("test"));


        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> recipeService.update(recipeCommand));
        verify(recipeRepository, times(1)).findById(recipeId);
        verify(recipeRepository, never()).save(any(Recipe.class));
    }

    @Test
    public void testUpdate_ThrowsException() {
        Long recipeId = 1L;
        RecipeCommand recipeCommand = new RecipeCommand(recipeId,"name", 3, "test", true, Set.of("test"));


        Recipe existingRecipe = Recipe.builder().id(recipeId).ingredients(Set.of("test")).build();
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(existingRecipe));
        doThrow(new RuntimeException("Error occurred during update")).when(recipeRepository).save(existingRecipe);

        assertThrows(RecipeUpdateCommandException.class, () -> recipeService.update(recipeCommand));
        verify(recipeRepository, times(1)).findById(recipeId);
        verify(recipeRepository, times(1)).save(existingRecipe);
    }

    @Test
    public void testDelete_RecipeExists() {
        Long recipeId = 1L;

        Recipe existingRecipe = Recipe.builder().id(recipeId).ingredients(Set.of("test")).build();
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(existingRecipe));

        recipeService.delete(recipeId);

        verify(recipeRepository, times(1)).findById(recipeId);
        verify(recipeRepository, times(1)).delete(existingRecipe);
    }

    @Test
    public void testDelete_RecipeNotFound() {
        Long recipeId = 1L;

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> recipeService.delete(recipeId));
        verify(recipeRepository, times(1)).findById(recipeId);
        verify(recipeRepository, never()).delete(any(Recipe.class));
    }

    @Test
    public void testGetRecipeById_RecipeExists() {
        Long recipeId = 1L;

        Recipe existingRecipe = Recipe.builder().id(recipeId).name("name").numServing(3).instructions("test").vegetarian(true).ingredients(Set.of("test")).build();
        RecipeQuery expectedRecipeQuery = new RecipeQuery(recipeId,"name", 3, "test", true, Set.of("test"));

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(existingRecipe));

        RecipeQuery recipeQuery = recipeService.getRecipeById(recipeId);

        assertEquals(expectedRecipeQuery, recipeQuery);
        verify(recipeRepository, times(1)).findById(recipeId);
    }

    @Test
    public void testGetRecipeById_RecipeNotFound() {
        Long recipeId = 1L;

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> recipeService.getRecipeById(recipeId));
        verify(recipeRepository, times(1)).findById(recipeId);
    }

    @Test
    public void testListAllRecipe() {
        Recipe recipe1 = Recipe.builder().id(1L).name("name").numServing(3).instructions("test").vegetarian(true).ingredients(Set.of("test")).build();
        Recipe recipe2 = Recipe.builder().id(2L).name("name").numServing(3).instructions("test").vegetarian(true).ingredients(Set.of("test")).build();

        List<Recipe> recipeList = Arrays.asList(recipe1, recipe2);

        RecipeQuery recipeQuery1 = new RecipeQuery(1L,"name", 3, "test", true, Set.of("test"));
        RecipeQuery recipeQuery2 = new RecipeQuery(2L,"name", 3, "test", true, Set.of("test"));

        List<RecipeQuery> expectedRecipeQueries = Arrays.asList(recipeQuery1, recipeQuery2);

        when(recipeRepository.findAll()).thenReturn(recipeList);

        List<RecipeQuery> recipeQueries = recipeService.listAll();

        assertEquals(expectedRecipeQueries, recipeQueries);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void testSearchRecipe() {
        Optional<String> nameOptional = Optional.of("Pizza");
        Optional<Integer> numServingOptional = Optional.of(4);
        Optional<String> instructionsOptional = Optional.empty();
        Optional<Boolean> vegetarianOptional = Optional.of(true);
        Optional<Set<String>> ingredientsInOptional = Optional.of(new HashSet<>(Arrays.asList("cheese", "tomato")));
        Optional<Set<String>> ingredientsOutOptional = Optional.empty();

        Recipe recipe1 = Recipe.builder().id(1L).name("name").numServing(3).instructions("test").vegetarian(true).ingredients(Set.of("test")).build();
        Recipe recipe2 = Recipe.builder().id(2L).name("name").numServing(3).instructions("test").vegetarian(true).ingredients(Set.of("test")).build();

        List<Recipe> recipeList = Arrays.asList(recipe1, recipe2);

        RecipeQuery recipeQuery1 = new RecipeQuery(1L,"name", 3, "test", true, Set.of("test"));
        RecipeQuery recipeQuery2 = new RecipeQuery(2L,"name", 3, "test", true, Set.of("test"));

        List<RecipeQuery> expectedRecipeQueries = Arrays.asList(recipeQuery1, recipeQuery2);

        when(recipeRepository.findAll((Specification<Recipe>) any())).thenReturn(recipeList);

        List<RecipeQuery> recipeQueries = recipeService.searchRecipe(nameOptional, numServingOptional, instructionsOptional, vegetarianOptional, ingredientsInOptional, ingredientsOutOptional);

        assertEquals(expectedRecipeQueries, recipeQueries);
        verify(recipeRepository, times(1)).findAll((Specification<Recipe>) any());
    }
}