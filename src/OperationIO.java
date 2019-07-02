import java.util.List;

public interface OperationIO {
    List<String> read(String[] args);
    void write(List<String> departmentsAvgSalary, List<String> transitions);
}