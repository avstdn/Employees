package ru.tsc.task2;

public class Main {
    public static void main(String[] args) {
        Calculation calculation = new Calculation();

        if (argsAreAbsent(args)) return;

        String inputAFilePath = args[0];
        String inputBFilePath = args[1];

        calculation.joinTablesImitation(inputAFilePath, inputBFilePath);
    }

    private static boolean argsAreAbsent(String[] args) {
        if (args.length > 1) {
            return false;
        } else if (args.length == 1) {
            System.out.println("Не указан путь ко второму файлу");
        } else {
            System.out.println("Не указан путь к файлам");
        }

        return true;
    }
}