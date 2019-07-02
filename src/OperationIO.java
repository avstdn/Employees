import java.util.List;

public interface OperationIO {
    List<String> read(String[] args);
    void write(List<String> averageSalary, List<String> transitions);
}