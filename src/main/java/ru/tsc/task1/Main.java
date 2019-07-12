package ru.tsc.task1;

/**
 * ЗАДАНИЕ:
 *
 * Написать программу, которая читает из файла информацию о сотрудниках
 * и их принадлежности к отделам, рассчитывает среднюю зарплату сотрудников
 * в отделе, строит и выводит в файл все варианты возможных переводов сотрудников
 * из одного отдела в другой, при которых средняя зарплата увеличивается в обоих отделах.
 */
public class Main {
    public static void main(String[] args) {
        Company company = new Company();

        if (argsAreAbsent(args)) return;

        String inputFile = args[0];
        String outputFile = args[1];

        company.calculateAverageAndTransitions(inputFile, outputFile);
    }

    private static boolean argsAreAbsent(String[] args) {
        if (args.length > 1) {
            return false;
        } else if (args.length == 1) {
            System.out.println("Не указан путь к выходному файлу");
        } else {
            System.out.println("Не указан путь к входному и выходному файлам");
        }

        return true;
    }
}