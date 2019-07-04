package ru.tsc.task1.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Department {
    private String name;
    private List<Employee> employees = new ArrayList<>();
    private BigDecimal averageSalary;
    private BigDecimal sumSalary;

    public String getName() {
        return name;
    }

    public Department(String name, Employee employee) {
        this.name = name;
        employees.add(employee);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        calculateSalary();
    }

    public void removeEmployee(Employee employee) {
        for (Employee currentEmployee : employees) {
            if (currentEmployee.equals(employee)) {
                employees.remove(employee);
                break;
            }
        }
        calculateSalary();
    }

    private int getEmployeesAmount() {
        return employees.size();
    }

    public BigDecimal getAverageSalary() {
        return averageSalary;
    }

    private void calculateSalary() {
        sumSalary = new BigDecimal(0);

        for (Employee employee : employees) {
            sumSalary = sumSalary.add(employee.getSalary());
        }

        averageSalary = sumSalary.divide(new BigDecimal(getEmployeesAmount()), 2, RoundingMode.FLOOR);
    }

    public BigDecimal getAverageSalaryFrom(Employee employee) {
        BigDecimal tempSumSalary = sumSalary.subtract(employee.getSalary());
        return tempSumSalary.divide(new BigDecimal(getEmployeesAmount() - 1), 2, RoundingMode.FLOOR);
    }

    public BigDecimal getAverageSalaryTo(Employee employee) {
        BigDecimal tempSumSalary = sumSalary.add(employee.getSalary());
        return tempSumSalary.divide(new BigDecimal(getEmployeesAmount() + 1), 2, RoundingMode.FLOOR);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return getName().equals(that.getName()) &&
                Objects.equals(getAverageSalary(), that.getAverageSalary()) &&
                Objects.equals(sumSalary, that.sumSalary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAverageSalary(), sumSalary);
    }
}