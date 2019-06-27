import java.io.*;
import java.util.*;

/**
 * Написать программу, которая читает из файла информацию о сотрудниках
 * и их принадлежности к отделам, рассчитывает среднюю зарплату сотрудников
 * в отделе, строит и выводит в файл все варианты возможных переводов сотрудников
 * из одного отдела в другой, при которых средняя зарплата увеличивается в обоих отделах.
 */
public class Main {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        String fileName = "resources\\employees_list.txt";

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(fileName));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "Cp1251"));

            String str;

            while ((str = bufferedReader.readLine()) != null) {
                list.add(str);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Employee> employees = new ArrayList<>();

        for (String e : list) {
            employees.add(new Employee(e));
        }

        List<Department> departments = getDepartments(employees);

        for (Department d : departments) {
            System.out.println(d.toString());
        }
    }

    public static List<Department> getDepartments(List<Employee> employees) {
        Map<String, Integer> departmentMap = new HashMap<>();

        for (Employee e : employees) {
            String departmentName = e.getDepartment();
            if (departmentMap.containsKey(departmentName)) {
                departmentMap.put(departmentName, departmentMap.get(departmentName) + 1);
            } else departmentMap.put(departmentName, 1);
        }

        List<Department> departments = new ArrayList<>();

        for (String d : departmentMap.keySet()) {
            int sumSalary = 0;
            for (Employee e : employees) {
                if (e.getDepartment().equals(d)) sumSalary += e.getSalary();
            }
            double avgSalary = sumSalary / departmentMap.get(d);
            departments.add(new Department(d, departmentMap.get(d), avgSalary));
        }

        return departments;
    }
}