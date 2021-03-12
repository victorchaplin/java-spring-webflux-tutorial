package com.example.webflux.tutorial.employee;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Repository
public class EmployeeRepository {
    private static final HashMap<String, Employee> employeeData;

    static {
        employeeData = new HashMap<>();
        employeeData.put("1",new Employee("1","Employee 1"));
        employeeData.put("2",new Employee("2","Employee 2"));
        employeeData.put("3",new Employee("3","Employee 3"));
        employeeData.put("4",new Employee("4","Employee 4"));
        employeeData.put("5",new Employee("5","Employee 5"));
        employeeData.put("6",new Employee("6","Employee 6"));
        employeeData.put("7",new Employee("7","Employee 7"));
        employeeData.put("8",new Employee("8","Employee 8"));
        employeeData.put("9",new Employee("9","Employee 9"));
        employeeData.put("10",new Employee("10","Employee 10"));
    }

    public Mono<Employee> findEmployeeById(String id) {
        return Mono.justOrEmpty(employeeData.getOrDefault(id, null));
    }

    public Flux<Employee> findAllEmployees() {
        return Flux.fromIterable(employeeData.values());
    }
}
