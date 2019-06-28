import java.io.IOException;
import java.util.*;

/**
 * ЗАДАНИЕ:
 *
 * Написать программу, которая читает из файла информацию о сотрудниках
 * и их принадлежности к отделам, рассчитывает среднюю зарплату сотрудников
 * в отделе, строит и выводит в файл все варианты возможных переводов сотрудников
 * из одного отдела в другой, при которых средняя зарплата увеличивается в обоих отделах.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        FileOperations fileOperations = new FileOperations();

        List<Employee> employees = getAllEmployees(fileOperations.readFile());
        List<Department> departments = getAllDepartments(employees);
        List<String> transitions = employeeTransitions(departments);

        fileOperations.writeFile(getAverageDepartmentsSalary(departments), transitions);
    }

    /**
     * Метод читает данные из файла и помещает их в список объектов типа Employee
     * @return Список всех работников, представленных в файле
     */
    private static List<Employee> getAllEmployees(List<String[]> employeesList) {
        List<Employee> employees = new ArrayList<>();

        for (String[] lines : employeesList) {
            employees.add(new Employee(lines));
        }

        return employees;
    }

    /**
     * Метод формирует список объектов типа Department на основе уже имеющего списка работников.
     * В каждый объект Department добавляется средняя зарплата по отделу и соответствующий
     * ему список работников
     * @param employees Список всех работников
     * @return Список всех отделов, представленных в файле
     */
    private static List<Department> getAllDepartments(List<Employee> employees) {
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

    /**
     * Метод формирует список всех возможных переходов работников из отдела в отдел, при которых
     * средняя зарплата увеличивается в обоих отделах
     * @param departments Список всех отделов
     * @return Список переводов работников
     */
    private static List<String> employeeTransitions(List<Department> departments) {
        List<String> transitionsList = new ArrayList<>();
        transitionsList.add("ПЕРЕВОДЫ:\n");
        for (int i = 0; i < departments.size(); i++) {
            for (int j = i + 1; j < departments.size(); j++) {
                Department firstDepartment = departments.get(i);
                Department secondDepartment = departments.get(j);

                employeePermutations(transitionsList, secondDepartment, firstDepartment);
                employeePermutations(transitionsList, firstDepartment, secondDepartment);
            }
        }

        return transitionsList;
    }

    /**
     * Метод сравнивает сотрудников 2х отделов на возможность перехода и добавляет их в список
     * переходов, если условия выполнены
     * @param transitionsList Список переводов работников
     * @param firstDepartment Первый отдел
     * @param secondDepartment Второй отдел
     */
    private static void employeePermutations(List<String> transitionsList, Department firstDepartment, Department secondDepartment) {
        for (Employee employee : secondDepartment.getEmployees()) {
            if (employee.getSalary() > firstDepartment.getAverageSalary()
                    && employee.getSalary() < secondDepartment.getAverageSalary()) {
                String transitions = employee.getFullName() + "(з/п " + employee.getSalary() + ")\n" +
                        " из отдела: " + secondDepartment.getName() +
                        "(текущая средняя з/п: " + secondDepartment.getAverageSalary() + ")" + "\n" +
                        " в отдел: " + firstDepartment.getName() +
                        "(текущая средняя з/п: " + firstDepartment.getAverageSalary() + ")\n";
                transitionsList.add(transitions);
            }
        }
    }

    /**
     * Метод расчитывает среднюю зарплату по каждому имеющемуся отделу
     * @param departments Список всех отделов
     * @return Список средних зарплат по всем отделам
     */
    private static List<String> getAverageDepartmentsSalary(List<Department> departments) {
        List<String> avgSalaryList = new ArrayList<>();
        avgSalaryList.add("СРЕДНЯЯ ЗАРПЛАТА ПО ОТДЕЛАМ:\n");

        for (Department department : departments) {
            String temp = department.getName() + " " + department.getAverageSalary() + " руб.\n";
            avgSalaryList.add(temp);
        }

        return avgSalaryList;
    }
}