package com.assignment.abn.exception;


public class RecipeUpdateCommandException extends RuntimeException {
    public RecipeUpdateCommandException(Long id) {
        super(String.format("Problem during update recipe with id: %s", id));
    }
}
