package ru.tsc.task1;

import ru.tsc.task1.entities.Department;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OperationFile implements IOperationIO {
    public List<Department> readInputFile(String inputFilePath) {
//        Map<String, Department> departmentsMap = new HashMap<>();
        List<Department> departments = new ArrayList<>();
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



//                if (departmentsMap.containsKey(departmentName)) {
//                    departmentsMap.get(departmentName).addEmployee(new Employee(employeeName, employeeSalary));
//                } else {
//                    departmentsMap.put(departmentName, new Department(departmentName, new Employee(employeeName, employeeSalary)));
//                }
            }

            scanner.close();
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }

//        return new ArrayList<>(departmentsMap.values());
        return departments;
    }

    public void writeOutputFile(String outputFilePath, List<String> averageSalary, List<String> transitions) {
        try {
            PrintWriter writer = new PrintWriter(outputFilePath);

            for (String departmentAvgSalary : averageSalary) {
                writer.println(departmentAvgSalary);
            }

            for (String transition : transitions) {
                writer.println(transition);
            }

            writer.close();
            System.out.println("Программа завершилась успешно, результаты работы записаны в файл");
        } catch (IOException e) {
            System.out.println("Ошибка записи файла");
        }
    }
}