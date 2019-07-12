package ru.tsc.task1;

import ru.tsc.task1.entities.Department;
import ru.tsc.task1.entities.Employee;
import ru.tsc.task1.io.IOperationIO;
import ru.tsc.task1.io.OperationFile;

import java.io.File;
import java.io.FileNotFoundException;
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
                Department tempStateFirstDepartment = new Department(initialStateDepartments.get(i));
                Department tempStateSecondDepartment = new Department(initialStateDepartments.get(j));

                List<String> firstDepartmentEmployeeTransfers = compareTransferDepartments(tempStateFirstDepartment, tempStateSecondDepartment);
                List<String> secondDepartmentEmployeeTransfers = compareTransferDepartments(tempStateSecondDepartment, tempStateFirstDepartment);

                employeeTransfers.addAll(firstDepartmentEmployeeTransfers);
                employeeTransfers.addAll(secondDepartmentEmployeeTransfers);
            }
        }

        return employeeTransfers;
    }

    private List<String> compareTransferDepartments(Department firstDepartment, Department secondDepartment) {
        List<String> firstDepartmentEmployeeTransfers = new ArrayList<>();
        List<Employee> firstDepartmentEmployees = firstDepartment.getEmployees();
        Iterator<Employee> employeesIterator = firstDepartmentEmployees.iterator();

        while (employeesIterator.hasNext()) {
            Employee firstDepartmentEmployee = employeesIterator.next();

                if (firstDepartmentEmployee.getSalary().compareTo(secondDepartment.getAverageSalary()) > 0
                        && firstDepartmentEmployee.getSalary().compareTo(firstDepartment.getAverageSalary()) < 0) {
                    employeesIterator.remove();
                    firstDepartment.removeEmployee(firstDepartmentEmployee);
                    secondDepartment.addEmployee(firstDepartmentEmployee);
                    String outputString = OutputFormatting.getTransitions(firstDepartmentEmployee, firstDepartment, secondDepartment);
                    firstDepartmentEmployeeTransfers.add(outputString);
                }
        }

        return firstDepartmentEmployeeTransfers;
    }

    private List<String> getDepartmentsAverageSalary(List<Department> departments) {
        List<String> departmentsAverageSalary = new ArrayList<>();
        departmentsAverageSalary.add(OutputFormatting.AVERAGE_NAME + "\n");

        for (Department department : departments) {
            StringBuilder tempOutputString = new StringBuilder();
            String formattedAverageSalary = OutputFormatting.getSalary(department.getName(), department.getAverageSalary());
            tempOutputString.append(formattedAverageSalary);

            for (Employee employee : department.getEmployees()) {
                tempOutputString.append(OutputFormatting.getEmployee(employee));
            }

            departmentsAverageSalary.add(tempOutputString.toString());
        }

        return departmentsAverageSalary;
    }

    private boolean fileIsEmpty(String inputFilePath) {
        try {
            Scanner scanner = new Scanner(new File(inputFilePath));
            scanner.nextLine();

            if (!scanner.hasNext()) {
                System.out.println("Файл пуст");
                return true;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл по указанному пути не найден");
            return true;
        }

        return false;
    }
}