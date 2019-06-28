import java.util.Arrays;

public class Employee {
    private String firstName;
    private String middleName;
    private String lastName;
    private String department;
    private double salary;

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public String getFullName() {
        return lastName + " " + firstName + " " + middleName;
    }

    public Employee(String[] lines) {
        String[] fullName = lines[0].split(" ");
        lastName = fullName[0];
        firstName = fullName[1];
        middleName = fullName[2];
        department = lines[1];
        salary = Integer.parseInt(lines[2]);
    }

    @Override
    public String toString() {
        return "Имя: " + getFirstName() + "\n" +
                "Отчество: " + getMiddleName() + "\n" +
                "Фамилия: " + getLastName() + "\n" +
                "Отдел: " + getDepartment() + "\n" +
                "Зарплата: " + getSalary() + " руб.\n";
    }
}