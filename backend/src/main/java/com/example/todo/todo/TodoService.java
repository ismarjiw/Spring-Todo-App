package com.example.todo.todo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    public void addNewTodo(Todo todo) {
        Optional<Todo> todoById = todoRepository
                .findTodoById(todo.getId());
        if (todoById.isPresent()) {
            throw new IllegalStateException(("id taken"));
        }
        todoRepository.save(todo);
    }

    public void deleteTodo(Long todoId) {
       boolean exists =  todoRepository.existsById(todoId);
       if (!exists) {
           throw new IllegalStateException(
                   "Todo with ID " + todoId + " does not exist.");
       }
       todoRepository.deleteById(todoId);
    }

    @Transactional
    public void updateTodo(Long todoId,
                           String newTitle,
                           String newDescription,
                           LocalDate newDueDate,
                           boolean newCompleted) {
        Optional<Todo> todoOptional = todoRepository.findById(todoId);

        if (todoOptional.isPresent()) {
            Todo todoToUpdate = todoOptional.get();
            if (newTitle != null) {
                todoToUpdate.setTitle(newTitle);
            }
            if (newDescription != null) {
                todoToUpdate.setDescription(newDescription);
            }
            if (newDueDate != null) {
                todoToUpdate.setDueDate(newDueDate);
            }
            boolean currentCompletedStatus = todoToUpdate.isCompleted();
            if (newCompleted != currentCompletedStatus) {
                todoToUpdate.setCompleted(!currentCompletedStatus);
            }
            todoRepository.save(todoToUpdate);
        } else {
            throw new EntityNotFoundException("Todo with ID " + todoId + " does not exist.");
        }
    }
    public Todo getTodoById(Long todoId) {
        Optional<Todo> todoOptional = todoRepository.findById(todoId);

        if (todoOptional.isPresent()) {
            return todoOptional.get(); // Return the Todo if found
        } else {
            // Handle the case where the Todo with the specified ID is not found
            throw new EntityNotFoundException("Todo with ID " + todoId + " does not exist.");
        }
    }
}
