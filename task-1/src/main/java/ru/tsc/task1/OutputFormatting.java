package ru.tsc.task1;

import ru.tsc.task1.entities.Department;
import ru.tsc.task1.entities.Employee;

import java.math.BigDecimal;
import java.util.List;

public class OutputFormatting {
    public static final String AVERAGE_NAME = "СРЕДНЯЯ ЗАРПЛАТА ПО ОТДЕЛАМ";
    public static final String TRANSITION_NAME = "ВОЗМОЖНЫЕ ПЕРЕВОДЫ СОТРУДНИКОВ";
    public static int employeeNameMaxLength = 0;
    public static int departmentNameMaxLength = 0;

    public static String getSalary(String departmentName, BigDecimal averageSalary) {
        String format = "%-"+ departmentNameMaxLength +"s %s%n";
        return String.format(format, departmentName, averageSalary + " руб.");
    }

    public static String getEmployee(Employee employee) {
        String format = "%-" + employeeNameMaxLength + "s %s%n";
        return "\t" + String.format(format, employee.getFullName(), employee.getSalary() + " руб.");
    }

    public static String getTransitions(List<Employee> employees, Department firstDepartment, BigDecimal firstDepartmentAverage, Department secondDepartment, BigDecimal secondDepartmentAverage) {
        StringBuilder outputTransitions = new StringBuilder();
        String format = "%-4s%-" + departmentNameMaxLength
                + "s %s%n%-4s%-" + departmentNameMaxLength
                + "s %s%n";

        outputTransitions.append(
                String.format(format,
                    "Из:", firstDepartment.getName(),
                    firstDepartmentAverage + " руб.",
                    "В:", secondDepartment.getName(),
                    secondDepartmentAverage + " руб."
                )
        );

        int employeeNumber = 0;
        for (Employee employee : employees) {
            outputTransitions.append(String.format("%s%n", "\t" + ++employeeNumber + ") "+ employee.getFullName()));
        }

        return outputTransitions.toString();
    }

    public static void calculateMaxLength(String employeeName, String departmentName) {
        if (employeeNameMaxLength < employeeName.length()) employeeNameMaxLength = employeeName.length();
        if (departmentNameMaxLength < departmentName.length()) departmentNameMaxLength = departmentName.length();
    }
}