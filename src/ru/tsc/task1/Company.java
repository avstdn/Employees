package ru.tsc.task1;

import ru.tsc.task1.entities.Department;
import ru.tsc.task1.entities.Employee;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Company {
    void calculateAverageAndTransitions(String inputFilePath, String outputFile) {
        if (fileIsEmpty(inputFilePath)) return;

        IOperationIO fileOperations = new OperationFile();
        List<Department> departments = fileOperations.readInputFile(inputFilePath);

        if (departments == null) return;

        List<String> allAverageSalary = getAllAverageSalary(departments);
        List<String> employeeTransitions = getEmployeeTransitions(departments);

        fileOperations.writeOutputFile(outputFile, allAverageSalary, employeeTransitions);
    }

    private List<String> getEmployeeTransitions(List<Department> departments) {
        List<String> transitionsList = new ArrayList<>();
        transitionsList.add(OutputFormatting.TRANSITION_NAME + "\n");

        for (int i = 0; i < departments.size(); i++) {
            for (int j = i + 1; j < departments.size(); j++) {
                Department firstDepartment = departments.get(i);
                Department secondDepartment = departments.get(j);

                compareDepartments(transitionsList, secondDepartment, firstDepartment);
                compareDepartments(transitionsList, firstDepartment, secondDepartment);
            }
        }

        return transitionsList;
    }

    private void compareDepartments(List<String> transitionsList, Department firstDepartment, Department secondDepartment) {
        for (Employee employee : secondDepartment.getEmployees()) {
            if (employee.getSalary().compareTo(firstDepartment.getAverageSalary()) > 0
                    && employee.getSalary().compareTo(secondDepartment.getAverageSalary()) < 0) {
                transitionsList.add(OutputFormatting.getTransitions(employee, firstDepartment, secondDepartment));
            }
        }
    }

    private List<String> getAllAverageSalary(List<Department> departments) {
        List<String> allAverageSalary = new ArrayList<>();
        allAverageSalary.add(OutputFormatting.AVERAGE_NAME + "\n");

        for (Department department : departments) {
            StringBuilder tempString = new StringBuilder();
            String formattedAverageSalary = OutputFormatting.getSalary(department.getName(), department.getAverageSalary());
            tempString.append(formattedAverageSalary);

            for (Employee employee : department.getEmployees()) {
                tempString.append(OutputFormatting.getEmployee(employee));
            }

            allAverageSalary.add(tempString.toString());
        }

        return allAverageSalary;
    }

    private boolean fileIsEmpty(String inputFilePath) {
        try {
            Scanner scanner = new Scanner(new File(inputFilePath));
            scanner.nextLine();

            if (!scanner.hasNext()) {
                System.out.println("Файл пуст");
                return true;
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
            return true;
        }

        return false;
    }
}