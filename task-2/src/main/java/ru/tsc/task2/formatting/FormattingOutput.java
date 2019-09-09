package ru.tsc.task2.formatting;

import ru.tsc.task2.entities.ResultRow;

import java.util.List;

public class FormattingOutput implements IOutput {
    private static final String ARRAYLIST_HEADER = "ArrayList:";
    private static final String LINKEDLIST_HEADER = "Отсортированный LinkedList:";
    private static final String HASHMAP_HEADER = "HashMap:";
    private static final int MIN_LENGTH_ID = 3;
    private static final int MIN_LENGTH_VALUE = 8;
    private static int maxLengthId;
    private static int maxLengthValue;

    @Override
    public String getFormattingText(List<ResultRow> arrayListResult, List<ResultRow> linkedListResult, List<ResultRow> hashMapListResult) {

        return ARRAYLIST_HEADER + "\n\n"
                + getFormatList(arrayListResult) + "\n"
                + LINKEDLIST_HEADER + "\n\n"
                + getFormatList(linkedListResult) + "\n"
                + HASHMAP_HEADER + "\n\n"
                + getFormatList(hashMapListResult);
    }

    private String getFormatList(List<ResultRow> list) {
        StringBuilder tempString = new StringBuilder();
        String format = "%-" + (maxLengthId > MIN_LENGTH_ID ? maxLengthId : MIN_LENGTH_ID) + "s%-"
                + (maxLengthValue > MIN_LENGTH_VALUE ? maxLengthValue : MIN_LENGTH_VALUE) + "s%-"
                + (maxLengthValue > MIN_LENGTH_VALUE ? maxLengthValue : MIN_LENGTH_VALUE) + "s%n";

        tempString.append(String.format(format, "ID", "A.VALUE", "B.VALUE"));

        for (ResultRow row : list) {
            int id = row.getId();
            String valueA = row.getValueA();
            String valueB = row.getValueB();

            tempString.append(String.format(format, id, valueA, valueB));
        }

        return tempString.toString();
    }

    public static void calculateMaxLength(int idField, String valueField) {
        int currentIdLength = String.valueOf(idField).length();
        int currentValueLength = valueField.length();

        if (maxLengthId < currentIdLength) maxLengthId = currentIdLength + 1;
        if (maxLengthValue < currentValueLength) maxLengthValue = currentValueLength + 1;
    }
}
