package com.example.EasyRoom.model.project;

import java.util.Objects;

public abstract class Contain {
    protected final String value;

    protected Contain(String value) {
        this.value = validateAndNormalize(value);
    }

    protected String validateAndNormalize(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(getEmptyErrorMessage());
        }
        if (value.length() < getMinLength()) {
            throw new IllegalArgumentException(
                String.format("Value must be at least %d characters long", getMinLength())
            );
        }
        if (value.length() > getMaxLength()) {
            throw new IllegalArgumentException(
                String.format("Value cannot exceed %d characters", getMaxLength())
            );
        }
        if (!containsOnlyValidCharacters(value)) {
            throw new IllegalArgumentException(getInvalidCharactersErrorMessage());
        }
        return value.trim();
    }

    protected abstract int getMinLength();
    protected abstract int getMaxLength();
    protected abstract String getEmptyErrorMessage();
    protected abstract String getInvalidCharactersErrorMessage();
    protected abstract boolean containsOnlyValidCharacters(String value);

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contain that = (Contain) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}