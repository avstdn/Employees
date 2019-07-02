import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Department {
    private String name;
    private List<Employee> employees = new ArrayList<>();
    private BigDecimal averageSalary;
    private BigDecimal sumSalary;

    public String getName() {
        return this.name;
    }

    public Department(String name, Employee employee) {
        this.name = name;
        this.employees.add(employee);
    }

    public List<Employee> getEmployees() {
        return this.employees;
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    public int getEmployeesAmount() {
        return this.employees.size();
    }

    public BigDecimal getAverageSalary() {
        return this.averageSalary;
    }

    public void calculateSalary() {
        this.sumSalary = new BigDecimal(0);

        for (Employee employee : employees) {
            this.sumSalary = this.sumSalary.add(employee.getSalary());
        }

        this.averageSalary = this.sumSalary.divide(new BigDecimal(this.getEmployeesAmount()), 2, RoundingMode.FLOOR);
    }

    public BigDecimal getAverageSalaryFrom(Employee employee) {
        BigDecimal tempSumSalary = sumSalary.subtract(employee.getSalary());
        return tempSumSalary.divide(new BigDecimal(this.getEmployeesAmount() - 1), 2, RoundingMode.FLOOR);
    }

    public BigDecimal getAverageSalaryTo(Employee employee) {
        BigDecimal tempSumSalary = sumSalary.add(employee.getSalary());
        return tempSumSalary.divide(new BigDecimal(this.getEmployeesAmount() + 1), 2, RoundingMode.FLOOR);
    }
}