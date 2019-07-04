package ru.tsc.task1;

import ru.tsc.task1.entities.Department;
import ru.tsc.task1.entities.Employee;

import java.math.BigDecimal;

public class OutputFormatting {
    public static final String AVERAGE_NAME = "СРЕДНЯЯ ЗАРПЛАТА ПО ОТДЕЛАМ";
    public static final String TRANSITION_NAME = "ПЕРЕВОДЫ";
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

    public static String getTransitions(Employee employee, Department firstDepartment, Department secondDepartment) {
        String format = "%s%n%-4s %-"
                + departmentNameMaxLength + "s %-32s %s%n%-4s %-"
                + departmentNameMaxLength + "s %-32s %s%n";

        return String.format(format,
                    employee.getFullName(),
                    "\t" + "Из:", secondDepartment.getName(),
                    "Средняя зарплата после перевода:",
                    secondDepartment.getAverageSalaryFrom(employee) + " руб.",
                    "\t" + "В:", firstDepartment.getName(),
                    "Средняя зарплата после перевода:",
                    firstDepartment.getAverageSalaryTo(employee) + " руб."
                );
    }

    public static void calculateMaxLength(String employeeName, String departmentName) {
        if (employeeNameMaxLength < employeeName.length()) employeeNameMaxLength = employeeName.length();
        if (departmentNameMaxLength < departmentName.length()) departmentNameMaxLength = departmentName.length();
    }
}