public class Department {
    private String name;
    private double averageSalary;
    private int employeeQuantity;

    public String getName() {
        return name;
    }

    public Double getAverageSalary() {
        return averageSalary;
    }

    public int getEmployeeQuantity() {
        return employeeQuantity;
    }

    public Department(String name, int employeeQuantity, double averageSalary) {
        this.name = name;
        this.employeeQuantity = employeeQuantity;
        this.averageSalary = averageSalary;
    }

    @Override
    public String toString() {
        return "Название департамента: " + this.getName() +
                "\nКоличество сотрудников: " + this.getEmployeeQuantity() +
                "\nСредняя зарплата: " + this.getAverageSalary();
    }
}