package ru.tsc.task1.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Department {
    private String name;
    private List<Employee> employees = new ArrayList<>();
    private BigDecimal averageSalary;
    private BigDecimal totalSalary;

    public String getName() {
        return name;
    }

    public Department(String name) {
        this.name = name;
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

    public int getEmployeesAmount() {
        return employees.size();
    }

    public BigDecimal getAverageSalary() {
        return averageSalary.setScale(2, RoundingMode.FLOOR);
    }

    public BigDecimal getTotalSalary() {
        return totalSalary.setScale(2, RoundingMode.FLOOR);
    }

    private void calculateSalary() {
        totalSalary = new BigDecimal(0);

        for (Employee employee : employees) {
            totalSalary = totalSalary.add(employee.getSalary());
        }

        averageSalary = totalSalary.divide(new BigDecimal(getEmployeesAmount()), 2, RoundingMode.FLOOR);
    }
}