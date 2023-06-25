package com.assignment.abn.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
class RecipeCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void saveRecipe_ValidRecipeCommand_ReturnsOkResponseWithRecipeId() throws Exception {
        String recipeCommandJson = "{\"name\":\"somename\"}";
        mockMvc.perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipeCommandJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    public void updateRecipe_ValidRecipeCommand_ReturnsOkResponseWithRecipeId() throws Exception {
        String recipeCommandJson = "{\"id\":\"2\", \"name\":\"somename\"}";
        mockMvc.perform(put("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipeCommandJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    public void updateRecipe_ReturnsNotFoundResponse() throws Exception {
        String recipeCommandJson = "{\"id\":\"22\", \"property2\":\"value2\"}";
        mockMvc.perform(put("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipeCommandJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteRecipeById_ValidId_ReturnsOkResponse() throws Exception {
        Long recipeId = 1L;
        mockMvc.perform(delete("/recipes/{recipe-id}", recipeId)).andExpect(status().isOk());
    }

    @Test
    public void deleteRecipeById_InValidId_ReturnsNotFoundResponse() throws Exception {
        Long recipeId = 123L;
        mockMvc.perform(delete("/recipes/{recipe-id}", recipeId)).andExpect(status().isNotFound());
    }

}
