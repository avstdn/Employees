import java.util.List;

public interface OperationIO {
    List<Department> read(String[] args);
    void write(List<String> averageSalary, List<String> transitions);
}