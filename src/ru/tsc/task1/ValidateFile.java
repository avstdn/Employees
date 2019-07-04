package ru.tsc.task1;

public class ValidateFile implements IValidateInput {
    private final static int MAX_COLUMNS_NUMBER = 3;
    private int lineNumber = 1;

    private void incrementLineNumber() {
        lineNumber++;
    }

    public boolean validateNewLines(String newLine) {
        String[] newLineFields = newLine.split(",");
        incrementLineNumber();

        return isCorrectLength(newLineFields) && isCorrectFields(newLineFields);
    }

    private boolean isCorrectLength(String[] newLineFields) {
        if (newLineFields.length < MAX_COLUMNS_NUMBER) {
            System.out.println("Отсутствуют необходимые поля (строка: " + lineNumber + ")");
            return false;
        } else if (newLineFields.length > MAX_COLUMNS_NUMBER) {
            System.out.println("Количество полей более " + MAX_COLUMNS_NUMBER + " (строка: " + lineNumber + ")");
            return false;
        } else if (newLineFields[0].trim().length() == 0) {
            System.out.println("Поле \"ФИО\" пусто (строка: " + lineNumber + ")");
            return false;
        } else if (newLineFields[1].trim().length() == 0) {
            System.out.println("Поле \"Отдел\" пусто (строка: " + lineNumber + ")");
            return false;
        } else if (newLineFields[2].trim().length() == 0) {
            System.out.println("Поле \"Зарплата\" пусто (строка: " + lineNumber + ")");
            return false;
        }

        return true;
    }

    private boolean isCorrectFields(String[] newLineFields) {
        String employeeName = newLineFields[0];
        String employeeSalary = newLineFields[2];

        if(!employeeName.matches("[а-яА-Я ]*")) {
            System.out.println("В поле \"ФИО\" есть недопустимые символы (строка: " + lineNumber + ")");
            return false;
        }

        try {
            Double.parseDouble(employeeSalary);
        } catch (NumberFormatException e) {
            System.out.println("Некорректное значение в поле \"Зарплата\" (строка: " + lineNumber + ")");
            return false;
        }

        return true;
    }
}