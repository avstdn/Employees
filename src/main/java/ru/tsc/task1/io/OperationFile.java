package ru.tsc.task1.io;

import ru.tsc.task1.OutputFormatting;
import ru.tsc.task1.entities.Department;
import ru.tsc.task1.entities.Employee;
import ru.tsc.task1.validation.IValidateInput;
import ru.tsc.task1.validation.ValidateFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class OperationFile implements IOperationIO {
    public List<Department> readInputFile(String inputFilePath) {
        Map<String, Department> departmentsMap = new HashMap<>();
        IValidateInput validateFile = new ValidateFile();

        try {
            Scanner scanner = new Scanner(new File(inputFilePath));
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String newLine = scanner.nextLine();

                if (!validateFile.validateNewLines(newLine)) return null;

                String[] newLineFields = newLine.split(",");

                for (int j = 0; j < newLineFields.length; j++) {
                    newLineFields[j] = newLineFields[j].trim();
                }

                String employeeName = newLineFields[0];
                String departmentName = newLineFields[1];
                BigDecimal employeeSalary = new BigDecimal(newLineFields[2]).setScale(2, RoundingMode.FLOOR);

                OutputFormatting.calculateMaxLength(employeeName, departmentName);

                Employee newEmployee = new Employee(employeeName, employeeSalary);
                Department newDepartment = new Department(departmentName);
                Department newEmployeeDepartment = departmentsMap.getOrDefault(departmentName, newDepartment);

                newEmployeeDepartment.addEmployee(newEmployee);
                departmentsMap.putIfAbsent(departmentName, newEmployeeDepartment);
            }

            scanner.close();
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }

        return new ArrayList<>(departmentsMap.values());
    }

    public void writeOutputFile(String outputFilePath, List<String> departmentsAverageSalary, List<String> transitions) {
        try {
            PrintWriter writer = new PrintWriter(outputFilePath);

            for (String departmentAvgSalary : departmentsAverageSalary) {
                writer.println(departmentAvgSalary);
            }

            for (String transition : transitions) {
                writer.println(transition);
            }

            writer.close();
            System.out.println("Программа завершилась успешно, результат записан в файл:\n" + outputFilePath);
        } catch (IOException e) {
            System.out.println("Ошибка записи файла");
        }
    }
}