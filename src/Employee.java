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

    public Employee(String line) {
        String[] split = line.split(", ");
        firstName = split[0].split(" ")[0];
        middleName = split[0].split(" ")[1];
        lastName = split[0].split(" ")[2];
        department = split[1];
        salary = Integer.parseInt(split[2]);
    }

    @Override
    public String toString() {
        return "Имя: " + getFirstName() +
                "\nОтчество: " + getMiddleName() +
                "\nФамилия: " + getLastName() +
                "\nОтдел: " + getDepartment() +
                "\nЗарплата: " + getSalary() + " руб.\n";
    }
}