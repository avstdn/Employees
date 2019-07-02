import java.math.BigDecimal;

public class Employee {
    private String fullName;
    private BigDecimal salary;

    public String getFullName() {
        return this.fullName;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public Employee(String fullName, BigDecimal salary) {
        this.fullName = fullName;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "\tФИО: " + this.getFullName() + "\n" + "\tЗарплата: " + this.getSalary() + " руб.\n";
    }
}