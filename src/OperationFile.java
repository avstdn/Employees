import java.io.*;
import java.util.*;

/**
 * Класс для работы с файлами (чтение/запись)
 */
public class OperationFile implements OperationIO {
    private static final String outputFile = "resources\\output.txt";

    public List<String> read(String[] args) {
        List<String> lines = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(args[0]));
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                lines.add(nextLine);
            }

            scanner.close();
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }

        return lines;
    }

    public void write(List<String> averageSalary, List<String> transitions) {
        try {
            PrintWriter writer = new PrintWriter(outputFile);

            for (String departmentAvgSalary : averageSalary) {
                writer.println(departmentAvgSalary);
            }

            for (String transition : transitions) {
                writer.println(transition);
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Возникла ошибка при записи файла");
        }
    }
}