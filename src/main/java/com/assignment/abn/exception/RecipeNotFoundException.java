package com.assignment.abn.exception;


public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(Long id) {
        super(String.format("Recipe not found with id: %s", id));
    }
}
