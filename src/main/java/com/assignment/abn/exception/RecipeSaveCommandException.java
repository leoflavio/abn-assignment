package com.assignment.abn.exception;


public class RecipeSaveCommandException extends RuntimeException {
    public RecipeSaveCommandException() {
        super(String.format("Problem during save recipe"));
    }
}
