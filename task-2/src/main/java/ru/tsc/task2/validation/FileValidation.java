package ru.tsc.task2.validation;

public class FileValidation implements IValidation {
    private final static int MAX_COLUMNS_NUMBER = 2;
    private final static int VALUE_MAX_LENGTH = 100;
    private int lineNumber = 1;

    private void incrementLineNumber() {
        lineNumber++;
    }

    @Override
    public boolean validateNewLine(String newLine) {
        String[] newLineFields = newLine.split("\t");
        incrementLineNumber();

        return isCorrectLength(newLineFields) && isCorrectField(newLineFields);
    }

    private boolean isCorrectLength(String[] newLineFields) {
        if (newLineFields.length < MAX_COLUMNS_NUMBER) {
            System.out.println("Отсутствуют необходимые поля (строка: " + lineNumber + ")");
            return false;
        } else if (newLineFields.length > MAX_COLUMNS_NUMBER) {
            System.out.println("Количество полей более " + MAX_COLUMNS_NUMBER + " (строка: " + lineNumber + ")");
            return false;
        } else if (newLineFields[0].trim().length() == 0) {
            System.out.println("Поле \"ID\" пусто (строка: " + lineNumber + ")");
            return false;
        } else if (newLineFields[1].trim().length() == 0) {
            System.out.println("Поле \"VALUE\" пусто (строка: " + lineNumber + ")");
            return false;
        }

        return true;
    }

    private boolean isCorrectField(String[] newLineFields) {
        String id = newLineFields[0];
        String value = newLineFields[1];

        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            System.out.println("Некорректное значение в поле \"ID\" (строка: " + lineNumber + ")");
            return false;
        }

        if (value.length() > VALUE_MAX_LENGTH) {
            System.out.println("Значение поля \"VALUE\" превышает допустимую длину (строка: " + lineNumber + ")");
            return false;
        }

        return true;
    }
}
