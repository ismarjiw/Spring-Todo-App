package com.example.todo.todo;

import org.springframework.beans.factory.annotation.Autowired;
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

    @DeleteMapping("/delete/{todoId}")
    public void deleteTodo(
            @PathVariable("todoId") Long todoId) {
        todoService.deleteTodo(todoId);
    }

    @PostMapping("/add-todo")
    public RedirectView addNewTodo(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("dueDate") String dueDate,
            @RequestParam(value = "completed", required = false, defaultValue = "false") boolean completed
    ) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setDueDate(LocalDate.parse(dueDate));
        todo.setCompleted(completed);
        todoService.addNewTodo(todo);

        return new RedirectView("/api/v1/todo");
    }

    @GetMapping("/update/{todoId}")
    public ModelAndView showEditForm(@PathVariable("todoId") Long todoId) {
        Todo todo = todoService.getTodoById(todoId);

        ModelAndView modelAndView = new ModelAndView("editTodo");
        modelAndView.addObject("todo", todo);

        return modelAndView;
    }

    @PostMapping("/update/{todoId}")
    public RedirectView updateTodo(
            @PathVariable("todoId") Long todoId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String dueDate,
            @RequestParam(required = false) boolean completed) {
        // Retrieve the existing TODO item
        Todo existingTodo = todoService.getTodoById(todoId);

        // Update the fields if provided in the form
        if (title != null) {
            existingTodo.setTitle(title);
        }
        if (description != null) {
            existingTodo.setDescription(description);
        }
        if (dueDate != null) {
            existingTodo.setDueDate(LocalDate.parse(dueDate));
        }
        existingTodo.setCompleted(completed);

        todoService.updateTodo(existingTodo);

        return new RedirectView("/api/v1/todo");
    }

}
