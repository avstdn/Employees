public class OutputFormatting {
    public static String getTransitions(Employee employee, Department firstDepartment, Department secondDepartment) {
        return employee.getFullName() + "\n" +
                " из отдела: " + secondDepartment.getName() +
                "(средняя зарплата после перевода: " + secondDepartment.getAverageSalaryFrom(employee) + ")" + "\n" +
                " в отдел: " + firstDepartment.getName() +
                "(средняя зарплата после перевода: " + firstDepartment.getAverageSalaryTo(employee) + ")\n";
    }
}