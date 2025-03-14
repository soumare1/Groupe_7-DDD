package com.example.EasyRoom.model.project;

public final class ProjectName extends Contain {
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 50;

    public ProjectName(String value) {
        super(value);
    }

    @Override
    protected int getMinLength() {
        return MIN_LENGTH;
    }

    @Override
    protected int getMaxLength() {
        return MAX_LENGTH;
    }

    @Override
    protected String getEmptyErrorMessage() {
        return "Project name cannot be empty";
    }

    @Override
    protected String getInvalidCharactersErrorMessage() {
        return "Project name can only contain letters, numbers, spaces and hyphens";
    }

    @Override
    protected boolean containsOnlyValidCharacters(String value) {
        return value.matches("^[a-zA-Z][a-zA-Z0-9\\s-]*$");
    }
}