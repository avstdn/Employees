import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с файлами (чтение/запись)
 */
public class FileOperations {
    private final String inputFile = "resources\\employees_list.txt";
    private final String outputFile = "resources\\output.txt";

    public List<String[]> readFile() throws IOException {
        List<String[]> lines = new ArrayList<>();

        FileInputStream fileInputStream = new FileInputStream(new File(inputFile));
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "Cp1251");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str;

        while ((str = bufferedReader.readLine()) != null && !str.isEmpty()) {
            String[] split = str.split(",");
            for (int i = 0; i < split.length; i++) {
                split[i] = split[i].trim();
            }
            lines.add(split);
        }

        bufferedReader.close();

        return lines;
    }

    public void writeFile(List<String> departmentsAvgSalary, List<String> transitions) throws IOException {
        PrintWriter writer = new PrintWriter(outputFile, StandardCharsets.UTF_8);
        String delimiter = "===========================\n";

        for (String departmentAvgSalary : departmentsAvgSalary) {
            writer.println(departmentAvgSalary);
        }

        writer.println(delimiter);

        for (String transition : transitions) {
            writer.println(transition);
        }

        writer.close();
    }
}
