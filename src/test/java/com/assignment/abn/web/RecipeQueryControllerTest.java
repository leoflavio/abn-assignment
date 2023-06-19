package com.assignment.abn.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
class RecipeQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getRecipeById_ExistingRecipeId_ReturnsOkResponseWithRecipe() throws Exception {
        Long recipeId = 2L;
        mockMvc.perform(get("/{id}", recipeId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getRecipeById_NonExistingRecipeId_ReturnsNotFoundResponse() throws Exception {
        Long nonExistingRecipeId = 456L;
        mockMvc.perform(get("/{id}", nonExistingRecipeId).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    public void getAllRecipe_ReturnsOkResponseWithListOfRecipes() throws Exception {
        mockMvc.perform(get("/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void searchRecipe_ReturnsOkResponseWithListOfRecipes() throws Exception {
        String name = "Chocolate cake";
        Integer numServing = 4;
        String instructions = "Oven 30 min";
        Boolean vegetarian = true;

        mockMvc.perform(get("/search")
                        .param("name", name)
                        .param("numServing", String.valueOf(numServing))
                        .param("instructions", instructions)
                        .param("vegetarian", String.valueOf(vegetarian))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}