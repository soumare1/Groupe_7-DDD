package com.example.EasyRoom.model.project;

public  final class ProjectDescription extends Contain {
    private static final int MIN_LENGTH = 100;
    private static final int MAX_LENGTH = 500;

    public ProjectDescription(String value) {
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
        return "Project description cannot be empty";
    }

    @Override
    public String getInvalidCharactersErrorMessage() {
        return "Project description can only contain letters, numbers, spaces and hyphens";
    }

    @Override
    protected boolean containsOnlyValidCharacters(String value) {
        return true;
    }

    public String getValue() {
        return value;
    }
}