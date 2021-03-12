package com.example.webflux.tutorial.employee;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmployeeApiTest {
    private final EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    private final EmployeeApi employeeApi = new EmployeeApi(employeeRepository);

    private final WebTestClient webTestClient = WebTestClient
            .bindToController(employeeApi)
            .configureClient()
            .baseUrl("/employees")
            .build();

    @Nested
    class GetEmployeeById {
        @Test
        void whenEmployeeExists_shouldReturnEmployeeAndStatus200() {
            when(employeeRepository.findEmployeeById("1"))
                    .thenReturn(Mono.just(new Employee("1","Employee 1")));

            var responseBody = new Employee("1","Employee 1");

            webTestClient
                    .get()
                    .uri("/1")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(Employee.class)
                    .isEqualTo(responseBody);
        }

        @Test
        void whenEmployeeNotFound_shouldReturnErrorAndStatus404() {
            when(employeeRepository.findEmployeeById("150"))
                    .thenReturn(Mono.empty());

            webTestClient
                    .get()
                    .uri("/150")
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody().isEmpty();
        }
    }
}