package ru.tsc.task2.input;

import ru.tsc.task2.entities.Row;
import ru.tsc.task2.formatting.FormattingOutput;
import ru.tsc.task2.validation.FileValidation;
import ru.tsc.task2.validation.IValidation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileOperation implements IOperation {

    @Override
    public List<Row> readFile(String inputFilePath) {
        List<Row> inputArrayList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(inputFilePath));
            IValidation validateFile = new FileValidation();

            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String newLine = scanner.nextLine();

                if (!validateFile.validateNewLine(newLine)) return null;

                String[] newLineFields = newLine.split("\t");

                for (int j = 0; j < newLineFields.length; j++) {
                    newLineFields[j] = newLineFields[j].trim();
                }

                int idField = Integer.parseInt(newLineFields[0]);
                String valueField = newLineFields[1];

                FormattingOutput.calculateMaxLength(idField, valueField);

                inputArrayList.add(new Row(idField, valueField));
            }

            scanner.close();
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }

        return new ArrayList<>(inputArrayList);
    }
}