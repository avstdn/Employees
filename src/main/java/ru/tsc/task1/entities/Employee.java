package ru.tsc.task1.entities;

import java.math.BigDecimal;
import java.util.Objects;

public class Employee {
    private String fullName;
    private BigDecimal salary;

    public String getFullName() {
        return this.fullName;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public Employee(String fullName, BigDecimal salary) {
        this.fullName = fullName;
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return getFullName().equals(employee.getFullName()) &&
                getSalary().equals(employee.getSalary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFullName(), getSalary());
    }
}