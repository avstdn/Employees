package ru.tsc.task1;

import ru.tsc.task1.entities.Department;

import java.util.List;

public interface IOperationIO {
    List<Department> readInputFile(String inputFilePath);
    void writeOutputFile(String outputFilePath, List<String> averageSalary, List<String> transitions);
}