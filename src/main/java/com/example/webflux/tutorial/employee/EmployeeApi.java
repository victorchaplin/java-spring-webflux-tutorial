package com.example.webflux.tutorial.employee;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
public class EmployeeApi {
    private final EmployeeRepository employeeRepository;

    public EmployeeApi(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // We wrap a single Employee resource in a Mono because we return at most one employee.
    @GetMapping("/{id}")
    private Mono<ResponseEntity<Employee>> getEmployeeById(@PathVariable String id) {
        return employeeRepository.findEmployeeById(id)
                .flatMap(employee -> Mono.just(ResponseEntity.ok(employee)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // For the collection resource, we use a Flux of type Employee – since that's the publisher for 0..n elements.
    @GetMapping
    private Flux<Employee> getAllEmployees() {
        return employeeRepository.findAllEmployees();
    }
}
