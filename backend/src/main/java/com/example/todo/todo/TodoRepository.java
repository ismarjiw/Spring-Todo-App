package com.example.todo.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Data Access Layer
@Repository
public interface TodoRepository
        extends JpaRepository<Todo, Long> {

    @Query("SELECT s FROM Todo s WHERE s.id = ?1")
    Optional<Todo> findTodoById(Long id);
}
