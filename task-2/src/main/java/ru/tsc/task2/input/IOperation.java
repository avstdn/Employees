package ru.tsc.task2.input;

import ru.tsc.task2.entities.Row;

import java.util.List;

public interface IOperation {
    List<Row> readFile(String inputFilePath);
}