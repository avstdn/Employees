package ru.tsc.task1;

import ru.tsc.task1.entities.Department;
import ru.tsc.task1.entities.Employee;
import ru.tsc.task1.io.IOperationIO;
import ru.tsc.task1.io.OperationFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

class Company {
    public void calculateAverageAndTransitions(String inputFilePath, String outputFilePath) {
        if (fileIsEmpty(inputFilePath)) return;

        IOperationIO fileOperations = new OperationFile();
        List<Department> initialStateDepartments = fileOperations.readInputFile(inputFilePath);

        if (initialStateDepartments == null) return;

        List<String> departmentsAverageSalary = getDepartmentsAverageSalary(initialStateDepartments);
        List<String> employeeTransfers = getEmployeeTransfers(initialStateDepartments);

        fileOperations.writeOutputFile(outputFilePath, departmentsAverageSalary, employeeTransfers);
    }

    private List<String> getEmployeeTransfers(List<Department> initialStateDepartments) {
        List<String> employeeTransfers = new ArrayList<>();
        employeeTransfers.add(OutputFormatting.TRANSITION_NAME + "\n");

        for (int i = 0; i < initialStateDepartments.size(); i++) {
            for (int j = i + 1; j < initialStateDepartments.size(); j++) {
                Department firstDepartment = new Department(initialStateDepartments.get(i));
                Department secondDepartment = new Department(initialStateDepartments.get(j));

                compareDepartments(employeeTransfers, firstDepartment, secondDepartment);
                compareDepartments(employeeTransfers, secondDepartment, firstDepartment);
            }
        }

        return employeeTransfers;
    }

    private void compareDepartments(List<String> employeeTransfers, Department firstDepartment, Department secondDepartment) {
        List<Employee> firstDepartmentEmployees = firstDepartment.getEmployees();
        Iterator<Employee> employeesIterator = firstDepartmentEmployees.iterator();

        while (employeesIterator.hasNext()) {
            Employee firstDepartmentEmployee = employeesIterator.next();

                if (firstDepartmentEmployee.getSalary().compareTo(secondDepartment.getAverageSalary()) > 0
                        && firstDepartmentEmployee.getSalary().compareTo(firstDepartment.getAverageSalary()) < 0) {
                    employeesIterator.remove();
                    firstDepartment.removeEmployee(firstDepartmentEmployee);
                    secondDepartment.addEmployee(firstDepartmentEmployee);
                    employeeTransfers.add(OutputFormatting.getTransitions(firstDepartmentEmployee, firstDepartment, secondDepartment));
                }
        }
    }

    private List<String> getDepartmentsAverageSalary(List<Department> departments) {
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