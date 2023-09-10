package com.example.todo.todo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public void updateTodo(Todo updatedTodo) {
    // Ensure that the updatedTodo has a valid ID
    Long todoId = updatedTodo.getId();
    if (todoId == null) {
        throw new IllegalArgumentException("TODO item must have a valid ID for updating.");
    }

    // Retrieve the existing TODO item from the database by its ID
    Optional<Todo> existingTodoOptional = todoRepository.findById(todoId);

    if (existingTodoOptional.isPresent()) {
        // Merge the changes from updatedTodo into the existingTodo
        Todo existingTodo = existingTodoOptional.get();
        existingTodo.setTitle(updatedTodo.getTitle());
        existingTodo.setDescription(updatedTodo.getDescription());
        existingTodo.setDueDate(updatedTodo.getDueDate());
        existingTodo.setCompleted(updatedTodo.isCompleted());

        // Save the updated TODO item
        todoRepository.save(existingTodo);
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
