package pro.sky.course2homeworkcollectionspart1.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.sky.course2homeworkcollectionspart1.Employee;
import pro.sky.course2homeworkcollectionspart1.exceptions.EmployeeAlreadyAddedException;
import pro.sky.course2homeworkcollectionspart1.exceptions.EmployeeNotFoundException;
import pro.sky.course2homeworkcollectionspart1.exceptions.EmployeeStorageIsFullException;

import java.util.Arrays;
import java.util.List;

public class EmployeeServiceTest {
    EmployeeService employeeService = new EmployeeService();

    @Test
    void getAll() {
        Employee empl1 = new Employee("petr", "petrov", 1, 15000.0);
        Employee empl2 = new Employee("oleg", "olegov", 1, 15000.0);
        employeeService.add(empl1);
        employeeService.add(empl2);
        List<Employee> expected = Arrays.asList(empl1, empl2);
        assertEquals(2, employeeService.getAll().size());
        assertIterableEquals(expected, employeeService.getAll());
    }

    @Test
    void add() {
        int startSize = employeeService.getAll().size();
        Employee empl1 = new Employee("petr", "petrov", 1, 15000.0);
        employeeService.add(empl1);
        assertEquals(startSize + 1, employeeService.getAll().size());
        assertTrue(employeeService.getAll().contains(empl1));
    }

    @Test
    void exceptionIfDublicatedAccount() {
        Employee empl1 = new Employee("petr", "petrov", 1, 15000.0);
        assertDoesNotThrow(() -> employeeService.add(empl1));
        assertThrows(EmployeeAlreadyAddedException.class, () -> employeeService.add(empl1));
    }

    @Test
    void exceptionIfStorageIsFull() {
        char firstChar = 'a';
        char lastChar = 'e';
        for (char ch = firstChar; ch <= lastChar; ch++) {
            Employee empl = new Employee("firstname" + ch, "lastname" + ch, 1, 1000.0);
//            System.out.println(empl);
            assertDoesNotThrow(() -> employeeService.add(empl));
        };
//        for (int i = 0; i < 5; i++) {
//            Employee e = new Employee("firstname_" + i, "lastname_" + i, 1, 1000);
//            assertDoesNotThrow(() -> employeeService.add(e));
//        }
        assertThrows(EmployeeStorageIsFullException.class, () ->
                employeeService.add(new Employee("petr", "petrov", 1, 15000.0)));
    }

    @Test
    void foundEmployee() {
        Employee emplExpected = new Employee("petr", "petrov", 1, 15000.0);
        employeeService.add(emplExpected);
        Employee emplActual = employeeService.find("petr", "petrov");
        assertNotNull(emplActual);
        assertEquals(emplExpected, emplActual);
    }

    @Test
    void notFoundEmployee() {
        Employee emplExpected = new Employee("petr", "petrov", 1, 15000.0);
        employeeService.add(emplExpected);
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.find("oleg", "olegov"));
    }

    @Test
    void remove() {
        Employee employee = new Employee("petr", "petrov", 1, 15000.0);
        employeeService.add(employee);
        assertTrue(employeeService.getAll().contains(employee));
        employeeService.remove("petr", "petrov");
        assertFalse(employeeService.getAll().contains(employee));
    }
}
