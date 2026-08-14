package savefile.objects;

import java.util.ArrayList;
import java.util.List;

public class Employment {
    private int provinceId;
    private int stateProvinceId;
    private List<Employee> employees;

    public Employment() {
        init();
    }

    public void init() {
        if (employees == null) {
            employees = new ArrayList<>();
        }
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getStateProvinceId() {
        return stateProvinceId;
    }

    public void setStateProvinceId(int stateProvinceId) {
        this.stateProvinceId = stateProvinceId;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }
}
