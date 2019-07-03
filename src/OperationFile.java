import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class OperationFile implements OperationIO {
    private static final String outputFile = "resources\\output.txt";

    public List<Department> read(String[] args) {
        Map<String, Department> departmentsMap = new HashMap<>();

        try {
            Scanner scanner = new Scanner(new File(args[0]));
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String[] split = scanner.nextLine().split(",");

                for (int j = 0; j < split.length; j++) {
                    split[j] = split[j].trim();
                }

                OutputFormatting.calculateMaxLength(split[0], split[1]);

                if (departmentsMap.containsKey(split[1])) {
                    departmentsMap.get(split[1]).addEmployee(new Employee(split[0], new BigDecimal(split[2])));
                } else {
                    departmentsMap.put(split[1], new Department(split[1], new Employee(split[0], new BigDecimal(split[2]))));
                }
            }

            scanner.close();
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }

        return new ArrayList<>(departmentsMap.values());
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
            System.out.println("Ошибка при записи файла");
        }
    }
}