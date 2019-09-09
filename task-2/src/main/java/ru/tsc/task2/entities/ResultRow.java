package ru.tsc.task2.entities;

import java.util.Objects;

public class ResultRow {
    private int id;
    private String valueA;
    private String valueB;

    public ResultRow(int id, String valueA, String valueB) {
        this.id = id;
        this.valueA = valueA;
        this.valueB = valueB;
    }

    public int getId() {
        return id;
    }

    public String getValueA() {
        return valueA;
    }

    public String getValueB() {
        return valueB;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValueA(String valueA) {
        this.valueA = valueA;
    }

    public void setValueB(String valueB) {
        this.valueB = valueB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResultRow)) return false;
        ResultRow resultRow = (ResultRow) o;
        return getId() == resultRow.getId() &&
                getValueA().equals(resultRow.getValueA()) &&
                getValueB().equals(resultRow.getValueB());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValueA(), getValueB());
    }
}