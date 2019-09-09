package ru.tsc.task2;

import ru.tsc.task2.entities.ResultRow;
import ru.tsc.task2.entities.Row;
import ru.tsc.task2.formatting.FormattingOutput;
import ru.tsc.task2.formatting.IOutput;
import ru.tsc.task2.input.FileOperation;
import ru.tsc.task2.input.IOperation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Calculation {
    void joinTablesImitation(String inputAFilePath, String inputBFilePath) {
        if (fileIsEmpty(inputAFilePath) || fileIsEmpty(inputBFilePath)) return;

        IOperation inputOperation = new FileOperation();

        List<Row> inputArrayListA = inputOperation.readFile(inputAFilePath);
        List<Row> inputArrayListB = inputOperation.readFile(inputBFilePath);

        if (inputArrayListA == null || inputArrayListB == null) return;

        inputArrayListA.sort(Comparator.comparing(Row::getId));
        inputArrayListB.sort(Comparator.comparing(Row::getId));

        List<Row> sortedLinkedListA = new LinkedList<>(inputArrayListA);
        List<Row> sortedLinkedListB = new LinkedList<>(inputArrayListB);

        Map<Integer, List<String>> hashMapListA = getHashMapList(inputArrayListA);
        Map<Integer, List<String>> hashMapListB = getHashMapList(inputArrayListB);

        List<ResultRow> arrayListResult = getArrayListResult(inputArrayListA, inputArrayListB);
        List<ResultRow> linkedListResult = getLinkedListResult(sortedLinkedListA, sortedLinkedListB);
        List<ResultRow> hashMapListResult = getHashMapListResult(hashMapListA, hashMapListB);

        IOutput outputResult = new FormattingOutput();
        System.out.print(outputResult.getFormattingText(arrayListResult, linkedListResult, hashMapListResult));
    }

    private List<ResultRow> getLinkedListResult(List<Row> sortedLinkedListA, List<Row> sortedLinkedListB) {
        List<ResultRow> linkedListResult = new LinkedList<>();
        ListIterator listAIterator = sortedLinkedListA.listIterator();
        ListIterator listBIterator = sortedLinkedListB.listIterator();
        Row rowTableA;
        Row rowTableB;

        while (listAIterator.hasNext() || listBIterator.hasNext()) {
            rowTableA = (Row) listAIterator.next();
            rowTableB = (Row) listBIterator.next();
            int idFieldTableA = rowTableA.getId();
            int idFieldTableB = rowTableB.getId();

            if (idFieldTableA == idFieldTableB) {
                linkedListResult.add(new ResultRow(idFieldTableA, rowTableA.getValue(), rowTableB.getValue()));

                if (listBIterator.hasNext()) {
                    listAIterator.previous();
                } else if (listAIterator.hasNext()) {
                    listBIterator = sortedLinkedListB.listIterator();
                }
            } else if (idFieldTableA < idFieldTableB) {
                if (listAIterator.hasNext()) {
                    listBIterator = sortedLinkedListB.listIterator();
                    listAIterator.remove();
                } else if (listBIterator.hasNext()) {
                    listAIterator.previous();
                }
            } else {
                if (listBIterator.hasNext() || listAIterator.hasNext()) {
                    listAIterator.previous();
                    listBIterator.remove();
                }
            }
        }

        return linkedListResult;
    }

    private boolean fileIsEmpty(String inputFilePath) {
        try {
            Scanner scanner = new Scanner(new File(inputFilePath));

            if (!scanner.hasNext()) {
                System.out.println("Файл пуст");
                return true;
            }
        } catch (FileNotFoundException e) {
            System.out.print("Файл по указанному пути не найден");
            return true;
        }

        return false;
    }

    private Map<Integer, List<String>> getHashMapList(List<Row> arrayList) {
        Map<Integer, List<String>> hashMapList = new HashMap<>();

        for (Row row : arrayList) {
            int rowId = row.getId();
            String rowValue = row.getValue();

            List<String> valuesList = hashMapList.getOrDefault(rowId, new ArrayList<>());
            valuesList.add(rowValue);
            hashMapList.put(rowId, valuesList);
        }

        return hashMapList;
    }

    private List<ResultRow> getArrayListResult(List<Row> inputArrayListA, List<Row> inputArrayListB) {
        List<ResultRow> arrayListResult = new ArrayList<>();

        for (Row rowA : inputArrayListA) {
            for (Row rowB : inputArrayListB) {
                int idA = rowA.getId();
                int idB = rowB.getId();

                if (idA == idB) {
                    arrayListResult.add(new ResultRow(idA, rowA.getValue(), rowB.getValue()));
                }
            }
        }

        return arrayListResult;
    }

    private List<ResultRow> getHashMapListResult(Map<Integer, List<String>> hashMapListA, Map<Integer, List<String>> hashMapListB) {
        List<ResultRow> hashMapListResult = new ArrayList<>();

        for (Integer rowId : hashMapListA.keySet()) {
            if (hashMapListB.containsKey(rowId)) {
                for (String valueA : hashMapListA.get(rowId)) {
                    for (String valueB : hashMapListB.get(rowId)) {
                        hashMapListResult.add(new ResultRow(rowId, valueA, valueB));
                    }
                }
            }
        }

        return hashMapListResult;
    }
}