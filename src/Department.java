import java.util.ArrayList;
import java.util.List;

public class Department {
    private String name;
    private double averageSalary;
    private int employeeQuantity;
    private List<Employee> employees;

    public String getName() {
        return name;
    }

    public Double getAverageSalary() {
        return averageSalary;
    }

    public int getEmployeeQuantity() {
        return employeeQuantity;
    }

    public List<Employee> getEmployees() {
        return employees;
    }


    public Department(String name, int employeeQuantity, double averageSalary, List<Employee> employees) {
        this.name = name;
        this.employeeQuantity = employeeQuantity;
        this.averageSalary = averageSalary;
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Отдел: " + this.getName() + "\n" +
                "Количество сотрудников: " + this.getEmployeeQuantity() + "\n" +
                "Средняя зарплата: " + this.getAverageSalary() + "\n";
    }
}