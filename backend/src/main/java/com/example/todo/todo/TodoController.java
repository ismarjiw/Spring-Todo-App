package com.example.todo.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

//    @GetMapping
//    public List<Todo> getTodos() {
//       return todoService.getTodos();
//    }

@GetMapping
public ModelAndView getTodos() {
    List<Todo> todos = todoService.getTodos();
    System.out.println(todos);

    ModelAndView modelAndView = new ModelAndView("list"); // Specify the Thymeleaf template name here
    modelAndView.addObject("todos", todos); // Add the list of todos as a model attribute

    return modelAndView;
}

    @PostMapping
    public void addNewTodo(@RequestBody Todo todo) {
        todoService.addNewTodo(todo);
    }

    @DeleteMapping(path = "{todoId}")
    public void deleteTodo(
            @PathVariable("todoId") Long todoId) {
        todoService.deleteTodo(todoId);
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
