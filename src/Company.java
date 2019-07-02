import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Company {
    private List<Department> departments = new ArrayList<>();

    public void run(String[] args) {
        ValidateInput validateFile = new ValidateFile();

        if (!validateFile.validate(args)) return;

        OperationIO operationFile = new OperationFile();
        List<String> input = operationFile.read(args);
        departments = this.getAllDepartments(input);
        this.calculateAllAverageSalary();

        List<String> transitions = this.employeeTransitions();

        operationFile.write(this.getAllAverageSalary(departments), transitions);
    }

    private void calculateAllAverageSalary() {
        for (Department department : departments) {
            department.calculateSalary();
        }
    }

    private List<Department> getAllDepartments(List<String> input) {
        Map<String, Department> departmentsMap = new HashMap<>();

        for (int i = 1; i < input.size(); i++) {
            String[] split = input.get(i).split(",");

            for (int j = 0; j < split.length; j++) {
                split[j] = split[j].trim();
            }

            if (departmentsMap.containsKey(split[1])) {
                departmentsMap.get(split[1]).addEmployee(new Employee(split[0], new BigDecimal(split[2])));
            } else {
                departmentsMap.put(split[1], new Department(split[1], new Employee(split[0], new BigDecimal(split[2]))));
            }
        }
        return new ArrayList<>(departmentsMap.values());
    }

    private List<String> employeeTransitions() {
        List<String> transitionsList = new ArrayList<>();
        transitionsList.add("ПЕРЕВОДЫ:\n");

        for (int i = 0; i < departments.size(); i++) {
            for (int j = i + 1; j < departments.size(); j++) {
                Department firstDepartment = departments.get(i);
                Department secondDepartment = departments.get(j);

                this.employeePermutations(transitionsList, secondDepartment, firstDepartment);
                this.employeePermutations(transitionsList, firstDepartment, secondDepartment);
            }
        }

        return transitionsList;
    }

    private void employeePermutations(List<String> transitionsList, Department firstDepartment, Department secondDepartment) {
        for (Employee employee : secondDepartment.getEmployees()) {
            if (employee.getSalary().compareTo(firstDepartment.getAverageSalary()) > 0
                    && employee.getSalary().compareTo(secondDepartment.getAverageSalary()) < 0) {
                transitionsList.add(OutputFormatting.getTransitions(employee, firstDepartment, secondDepartment));
            }
        }
    }

    private static List<String> getAllAverageSalary(List<Department> departments) {
        List<String> avgSalaryList = new ArrayList<>();
        avgSalaryList.add("СРЕДНЯЯ ЗАРПЛАТА ПО ОТДЕЛАМ:\n");

        for (Department department : departments) {
            String temp = department.getName() + " " + department.getAverageSalary() + " руб.\n";
            avgSalaryList.add(temp);
        }

        return avgSalaryList;
    }
}