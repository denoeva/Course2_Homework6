package pro.sky.course2homeworkcollectionspart1.service;

import org.springframework.stereotype.Service;
import pro.sky.course2homeworkcollectionspart1.Employee;
import pro.sky.course2homeworkcollectionspart1.exceptions.EmployeeAlreadyAddedException;
import pro.sky.course2homeworkcollectionspart1.exceptions.EmployeeNotFoundException;
import pro.sky.course2homeworkcollectionspart1.exceptions.EmployeeStorageIsFullException;

import java.util.*;

@Service
public class EmployeeService {
    private static final int SIZE_LIMIT = 5;
    private final Map<String, Employee> employees = new HashMap<>(SIZE_LIMIT);

    public Collection<Employee> getAll() {
        return employees.values();
    }

    public Employee add(Employee employee) {
        if (employees.size() >= SIZE_LIMIT) {
            throw new EmployeeStorageIsFullException();
        }
        if (employees.containsKey(createEmployeeKey(employee))) {
            throw new EmployeeAlreadyAddedException();
        }
        employees.put(createEmployeeKey(employee), employee);
        return employee;
    }

    public Employee find(String firstName, String lastName) {
        Employee employee = employees.get(createEmployeeKey(firstName, lastName));
        if (employee == null) {
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public Employee remove(String firstName, String lastName) {
        return employees.remove(createEmployeeKey(firstName, lastName));
    }

    public static String createEmployeeKey(Employee employee) {
            return createEmployeeKey(employee.getFirstName(), employee.getLastName());
    }

    private static String createEmployeeKey(String firstName, String lastName) {
        return (firstName + lastName).toLowerCase();
    }
}
