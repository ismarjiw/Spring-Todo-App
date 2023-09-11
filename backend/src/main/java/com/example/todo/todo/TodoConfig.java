package com.example.todo.todo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class TodoConfig {
    @Bean
    CommandLineRunner commandLineRunner(TodoRepository repository) {
        return args -> {
            Todo one = new Todo(
                    "Go to Target",
                    "Buy toilet paper",
                    LocalDate.of(2023, 9, 5),
                    true
            );

            Todo two = new Todo(
                    "Go to Whole Foods",
                    "Buy wine",
                    LocalDate.of(2023, 9, 5),
                    false
            );

            repository.saveAll(
                    List.of(one, two)
            );
        };
    }
}
