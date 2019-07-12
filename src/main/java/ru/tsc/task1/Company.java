package ru.tsc.task1;

import ru.tsc.task1.entities.Department;
import ru.tsc.task1.entities.Employee;
import ru.tsc.task1.io.IOperationIO;
import ru.tsc.task1.io.OperationFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Company {
    public void calculateAverageAndTransitions(String inputFilePath, String outputFilePath) {
        if (fileIsEmpty(inputFilePath)) return;

        IOperationIO fileOperations = new OperationFile();
        List<Department> allDepartmentsList = fileOperations.readInputFile(inputFilePath);

        if (allDepartmentsList == null) return;

        List<String> departmentsAverageSalary = getDepartmentsAverageSalary(allDepartmentsList);
        List<String> allEmployeeTransfers = getAllEmployeesTransfers(allDepartmentsList);

        fileOperations.writeOutputFile(outputFilePath, departmentsAverageSalary, allEmployeeTransfers);
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

    private List<String> getAllEmployeesTransfers(List<Department> allDepartmentsList) {
        List<String> allEmployeesTransfers = new ArrayList<>();
        allEmployeesTransfers.add(OutputFormatting.TRANSITION_NAME + "\n");

        for (int i = 0; i < allDepartmentsList.size(); i++) {
            Department firstDepartment = allDepartmentsList.get(i);

            for (int j = 0; j < allDepartmentsList.size(); j++) {
                if (i == j) continue;

                Department secondDepartment = allDepartmentsList.get(j);
                List<String> employeesTransfersBetweenTwoDepartments = getEmployeesTransfersBetweenTwoDepartments(firstDepartment, secondDepartment);
                allEmployeesTransfers.addAll(employeesTransfersBetweenTwoDepartments);
            }
        }

        return allEmployeesTransfers;
    }

    private List<String> getEmployeesTransfersBetweenTwoDepartments(Department firstDepartment, Department secondDepartment) {
        List<String> firstDepartmentEmployeeTransfers = new ArrayList<>();
        List<Employee> firstDepartmentEmployees = firstDepartment.getEmployees();

        int bitsCount = (int) Math.pow(2, firstDepartment.getEmployeesAmount());

        for (int i = 1; i < bitsCount; i++) {
            List<Employee> subListEmployeesTransitions = new ArrayList<>();

            BigDecimal firstDepartmentTotalSalary = getFirstDepartmentTotalSalary(i, firstDepartmentEmployees, subListEmployeesTransitions);
            BigDecimal firstDepartmentAverageSalary = firstDepartmentTotalSalary.divide(new BigDecimal(subListEmployeesTransitions.size()), 2, RoundingMode.FLOOR);

            if (firstDepartmentAverageSalary.compareTo(secondDepartment.getAverageSalary()) > 0
                    && firstDepartmentAverageSalary.compareTo(firstDepartment.getAverageSalary()) < 0) {

                BigDecimal newAverageForFirstDepartment = calculateNewAverageForFirstDepartment(firstDepartment, subListEmployeesTransitions.size(), firstDepartmentTotalSalary);
                BigDecimal newAverageForSecondDepartment = calculateNewAverageForSecondDepartment(secondDepartment, subListEmployeesTransitions.size(), firstDepartmentTotalSalary);

                String outputString = OutputFormatting.getTransitions(subListEmployeesTransitions, firstDepartment, newAverageForFirstDepartment, secondDepartment, newAverageForSecondDepartment);

                firstDepartmentEmployeeTransfers.add(outputString);
            }
        }

        return firstDepartmentEmployeeTransfers;
    }

    private BigDecimal getFirstDepartmentTotalSalary(int bitNumber, List<Employee> firstDepartmentEmployees, List<Employee> subListEmployeesTransitions) {
        int bitsIndex = 0;
        BigDecimal employeesTotalSalary = new BigDecimal(0);

        while (bitNumber > 0) {
            if ((bitNumber & 1) == 1) {
                subListEmployeesTransitions.add(firstDepartmentEmployees.get(bitsIndex));
                employeesTotalSalary = employeesTotalSalary.add(firstDepartmentEmployees.get(bitsIndex).getSalary());
            }

            bitNumber >>= 1;
            bitsIndex++;
        }

        return employeesTotalSalary;
    }

    private BigDecimal calculateNewAverageForFirstDepartment(Department firstDepartment, int employeesTransferAmount, BigDecimal employeesTotalSalary) {
        BigDecimal totalSalaryFirstDepartment = firstDepartment.getTotalSalary().subtract(employeesTotalSalary);
        int employeesAmountFirstDepartment = firstDepartment.getEmployeesAmount() - employeesTransferAmount;

        return totalSalaryFirstDepartment.divide(new BigDecimal(employeesAmountFirstDepartment), 2, RoundingMode.FLOOR);
    }

    private BigDecimal calculateNewAverageForSecondDepartment (Department secondDepartment, int employeesTransferAmount, BigDecimal employeesTotalSalary) {
        BigDecimal totalSalarySecondDepartment = secondDepartment.getTotalSalary().add(employeesTotalSalary);
        int employeesAmountSecondDepartment = secondDepartment.getEmployeesAmount() + employeesTransferAmount;

        return totalSalarySecondDepartment.divide(new BigDecimal(employeesAmountSecondDepartment), 2, RoundingMode.FLOOR);
    }
}