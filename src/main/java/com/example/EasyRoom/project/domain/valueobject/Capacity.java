package com.example.EasyRoom.project.domain.valueobject;

import java.util.Objects;

public class Capacity {
    private final Integer value;

    public Capacity(Integer value) { //verifie si c au moins 1
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive integer");
        }
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Capacity that = (Capacity) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}