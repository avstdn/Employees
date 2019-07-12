package ru.tsc.task1.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Department {
    private String name;
    private List<Employee> employees = new ArrayList<>();
    private BigDecimal averageSalary;

    public String getName() {
        return name;
    }

    public Department(String name) {
        this.name = name;
    }

    public Department(Department department) {
        this.name = department.name;
        this.averageSalary = department.averageSalary;
        this.employees.addAll(department.getEmployees());
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
        return averageSalary.setScale(2, RoundingMode.FLOOR);
    }

    private void calculateSalary() {
        BigDecimal partSumSalary = new BigDecimal(0);

        for (Employee employee : employees) {
            partSumSalary = partSumSalary.add(employee.getSalary());
        }

        averageSalary = partSumSalary.divide(new BigDecimal(getEmployeesAmount()), 2, RoundingMode.FLOOR);
    }
}