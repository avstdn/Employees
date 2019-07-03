import java.util.ArrayList;
import java.util.List;

class Company {
    private List<Department> departments = new ArrayList<>();

    void run(String[] args) {
        ValidateInput validateFile = new ValidateFile();

        if (!validateFile.validate(args)) return;

        OperationIO operationFile = new OperationFile();
        departments = operationFile.read(args);

        this.calculateAllAverageSalary();

        operationFile.write(this.getAllAverageSalary(departments), this.getEmployeeTransitions());
    }

    private void calculateAllAverageSalary() {
        for (Department department : departments) {
            department.calculateSalary();
        }
    }

    private List<String> getEmployeeTransitions() {
        List<String> transitionsList = new ArrayList<>();
        transitionsList.add(OutputFormatting.TRANSITION_NAME + "\n");

        for (int i = 0; i < departments.size(); i++) {
            for (int j = i + 1; j < departments.size(); j++) {
                Department firstDepartment = departments.get(i);
                Department secondDepartment = departments.get(j);

                this.compareDepartments(transitionsList, secondDepartment, firstDepartment);
                this.compareDepartments(transitionsList, firstDepartment, secondDepartment);
            }
        }

        return transitionsList;
    }

    private void compareDepartments(List<String> transitionsList, Department firstDepartment, Department secondDepartment) {
        for (Employee employee : secondDepartment.getEmployees()) {
            if (employee.getSalary().compareTo(firstDepartment.getAverageSalary()) > 0
                    && employee.getSalary().compareTo(secondDepartment.getAverageSalary()) < 0) {
                transitionsList.add(OutputFormatting.getTransitions(employee, firstDepartment, secondDepartment));
            }
        }
    }

    private List<String> getAllAverageSalary(List<Department> departments) {
        List<String> avgSalaryList = new ArrayList<>();
        avgSalaryList.add(OutputFormatting.AVERAGE_NAME + "\n");

        for (Department department : departments) {
            StringBuilder temp = new StringBuilder();
            temp.append(OutputFormatting.getSalary(department.getName(), department.getAverageSalary()));

            for (Employee employee : department.getEmployees()) {
                temp.append(OutputFormatting.getEmployee(employee));
            }

            avgSalaryList.add(temp.toString());
        }

        return avgSalaryList;
    }
}