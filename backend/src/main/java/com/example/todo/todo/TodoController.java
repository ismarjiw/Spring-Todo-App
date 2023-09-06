package com.example.todo.todo;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/todo")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

@GetMapping
public ModelAndView getTodos() {
    List<Todo> todos = todoService.getTodos();

    ModelAndView modelAndView = new ModelAndView("list"); // Specify the Thymeleaf template name here
    modelAndView.addObject("todos", todos); // Add the list of todos as a model attribute

    return modelAndView;
}

    @DeleteMapping(path = "{todoId}")
    public void deleteTodo(
            @PathVariable("todoId") Long todoId) {
        todoService.deleteTodo(todoId);
    }

    @PostMapping("/add-todo")
    public RedirectView addNewTodo(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("dueDate") String dueDate,
            @RequestParam("completed") boolean completed
    ) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setDueDate(LocalDate.parse(dueDate));
        todo.setCompleted(completed);
        todoService.addNewTodo(todo);

        return new RedirectView("/api/v1/todo");
    }

    @PutMapping(path = "{todoId}")
    public void updateTodo(
            @PathVariable("todoId") Long todoId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) LocalDate dueDate,
            @RequestParam(required = false) boolean completed) {
        todoService.updateTodo(todoId, title, description, dueDate, completed);
    }

}
