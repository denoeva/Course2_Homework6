package pro.sky.course2homeworkcollectionspart1.service;

import org.springframework.stereotype.Service;
import pro.sky.course2homeworkcollectionspart1.Employee;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public  double getEmployeeSalarySum(int department) {
        return employeeService.getAll().stream()
                .filter(empl -> empl.getDepartment() == department)
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    public Employee getEmployeeWithMaxSalary(int department) {
        return employeeService.getAll().stream()
                .filter(empl -> empl.getDepartment() == department)
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElse(null);
    }

    public Employee getEmployeeWithMinSalary(int department) {
        return employeeService.getAll().stream()
                .filter(empl -> empl.getDepartment() == department)
                .min(Comparator.comparingDouble(Employee::getSalary))
                .orElse(null);
    }

    public List<Employee> getEmployeesFromDepartment(int department) {
        return employeeService.getAll().stream()
                .filter(empl -> empl.getDepartment() == department)
                .collect(Collectors.toList());
    }

    public Map<Integer, List<Employee>> getEmployeesGroupedByDepartment() {
        return employeeService.getAll().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }
}
