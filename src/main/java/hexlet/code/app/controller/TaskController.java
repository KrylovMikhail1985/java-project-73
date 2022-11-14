package hexlet.code.app.controller;

import hexlet.code.app.dto.TaskDto;
import hexlet.code.app.model.Task;
import hexlet.code.app.service.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    private TaskServiceImpl taskService;
    @GetMapping("/tasks/{id}")
    public Task findTaskById(@PathVariable(name = "id") long id) {
        return taskService.fineTaskById(id);
    }
    @GetMapping("/tasks")
    public List<Task> findAllTasks() {
        return taskService.findAllTasks();
    }
    @PostMapping("/tasks")
    public Task createNewTask(@RequestBody TaskDto taskDto) {
        return taskService.createNewTask(taskDto);
    }
    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable(name = "id") long id,
                           @Valid @RequestBody TaskDto taskDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            throw new RuntimeException();
        }
        return taskService.updateTask(id, taskDto);
    }
    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable(name = "id") long id) {
        taskService.deleteTask(id);
    }
}
