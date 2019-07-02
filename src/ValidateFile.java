import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ValidateFile implements ValidateInput {
    public boolean validate(String[] args) {
        try {
            Scanner scanner = new Scanner(new File(args[0]));
            scanner.nextLine();
            int lineNumber = 2;

            while (scanner.hasNextLine()) {
                String[] splitText = scanner.nextLine().split(",");

                if (splitText.length < 3) {
                    System.out.println("Отсутствуют необходимые поля (строка: " + lineNumber + ")");
                    return false;
                } else if (splitText[0].trim().length() == 0) {
                    System.out.println("Поле \"ФИО\" пусто (строка: " + lineNumber + ")");
                    return false;
                } else if (splitText[1].trim().length() == 0) {
                    System.out.println("Поле \"Отдел\" пусто (строка: " + lineNumber + ")");
                    return false;
                } else if (splitText[2].trim().length() == 0) {
                    System.out.println("Поле \"Зарплата\" пусто (строка: " + lineNumber + ")");
                    return false;
                }

                if(!splitText[0].matches("[а-яА-Я ]*")) {
                    System.out.println("В поле \"ФИО\" есть недопустимые символы (строка: " + lineNumber + ")");
                    return false;
                }

                try {
                    Double.parseDouble(splitText[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Некорректное значение в поле \"Зарплата\" (строка: " + lineNumber + ")");
                    return false;
                }

                lineNumber++;
            }

            scanner.close();
        } catch (RuntimeException e) {
            System.out.println("Не указан путь к файлу");
            return false;
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден по указанному пути");
            return false;
        }

        return true;
    }
}