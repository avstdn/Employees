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

        printPermutation(departments);
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
            List<Employee> employeeDepartment = new ArrayList<>();

            for (Employee e : employees) {
                if (e.getDepartment().equals(d)) {
                    sumSalary += e.getSalary();
                    employeeDepartment.add(e);
                }
            }
            double avgSalary = sumSalary / departmentMap.get(d);
            departments.add(new Department(d, departmentMap.get(d), avgSalary, employeeDepartment));
        }

        return departments;
    }

    public static void printPermutation(List<Department> departments) {
        for (int i = 0; i < departments.size(); i++) {
            for (int j = i + 1; j < departments.size(); j++) {
                Department firstDepartment = departments.get(i);
                Department lastDepartment = departments.get(j);

                for (Employee e : firstDepartment.getEmployees()) {
                    if (e.getSalary() > lastDepartment.getAverageSalary()
                            && e.getSalary() < firstDepartment.getAverageSalary()) {
                        System.out.println("employee: " + e);
                        System.out.println("first: " + firstDepartment.getName());
                        System.out.println("last: " + lastDepartment.getName());
                        System.out.println("first avg salary: " + firstDepartment.getAverageSalary());
                        System.out.println("last avg salary: " + lastDepartment.getAverageSalary());
                    }
                }
                System.out.println("===============");
            }
        }
    }
}