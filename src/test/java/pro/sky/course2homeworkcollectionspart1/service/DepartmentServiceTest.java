package pro.sky.course2homeworkcollectionspart1.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.course2homeworkcollectionspart1.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @Mock
    EmployeeService employeeService;

    @InjectMocks
    DepartmentService departmentService;

    List<Employee> employees = Arrays.asList(
            new Employee("oleg", "olegov", 1, 300.0),
            new Employee("petr", "petrov", 2, 100.0),
            new Employee("ivan", "ivanov", 1, 200.0),
            new Employee("sasha", "sashov", 2, 100.0),
            new Employee("gosha", "goshov", 1, 100.0)
    );

    @BeforeEach
    void setup() {
        when(employeeService.getAll()).thenReturn(employees);
    }

//    @Test
//    void test() {
//        Employee empl = new Employee("oleg", "olegov", 5, 500);
//        Mockito.when(employeeService.find(anyString(), anyString())).thenReturn(empl);
//        System.out.println(employeeService.find("oleg", "olegov"));
//    }

    @Test
    void sum() {
        double salaryActual = departmentService.getEmployeeSalarySum(1);
        assertEquals(600.0, salaryActual, 0.000001);
    }

//    @Test
//    void min() {
//        double salaryActual = departmentService.getEmployeeWithMinSalary(1);
//        assertEquals(100.0, salaryActual, 0.000001);
//    }
//
//    @Test
//    void max() {
//        double salaryActual = departmentService.getEmployeeWithMaxSalary(1);
//        assertEquals(300.0, salaryActual, 0.000001);
//    }

    @Test
    void getAllFromDepartment() {
        List<Employee> listActual = departmentService.getEmployeesFromDepartment(2);
        List<Employee> listExpected = Arrays.asList(
                new Employee("petr", "petrov", 2, 100.0),
                new Employee("sasha", "sashov", 2, 100.0)
        );
        assertEquals(listExpected.size(), listActual.size());
        assertTrue(listExpected.containsAll(listActual));
    }

    @Test
    void getAll() {
        List<Integer> departmentsExpected = employees.stream()
                .map(Employee::getDepartment)
                .distinct()
                .collect(Collectors.toList());
        Map<Integer, List<Employee>> departmentsActual = departmentService.getEmployeesGroupedByDepartment();
        assertEquals(departmentsExpected.size(), departmentsActual.keySet().size());
        assertTrue(departmentsExpected.containsAll(departmentsActual.keySet()));
        verify(employeeService).getAll();
        verify(employeeService, times(0)).remove(anyString(), anyString());
    }
}
