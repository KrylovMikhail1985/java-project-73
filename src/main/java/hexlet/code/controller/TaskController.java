package hexlet.code.controller;

import com.querydsl.core.types.Predicate;
import hexlet.code.dto.TaskDto;
import hexlet.code.model.Task;
import hexlet.code.service.TaskServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
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

@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    private TaskServiceImpl taskService;
    @Operation(summary = "Get current Tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks was received"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @GetMapping("/tasks/{id}")
    public Task findTaskById(@Parameter(description = "Task's ID") @PathVariable(name = "id") long id) {
        return taskService.fineTaskById(id);
    }
    @Operation(summary = "Get all Tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Tasks were received"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @GetMapping("/tasks")
    public Iterable<Task> findAllTasks(@QuerydslPredicate(root = Task.class) Predicate predicate) {
        return taskService.findAllTasks(predicate);
    }
    @Operation(summary = "Create new Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task was successfully created"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @PostMapping("/tasks")
    public Task createNewTask(@Parameter(description = "Task's body") @RequestBody TaskDto taskDto) {
        return taskService.createNewTask(taskDto);
    }
    @Operation(summary = "Update current Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task was successfully updated"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @PutMapping("/tasks/{id}")
    public Task updateTask(@Parameter(description = "Task's ID") @PathVariable(name = "id") long id,
                           @Parameter(description = "Task's body") @Valid @RequestBody TaskDto taskDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            throw new RuntimeException();
        }
        return taskService.updateTask(id, taskDto);
    }
    @Operation(summary = "Delete current Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task was successfully deleted"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@Parameter(description = "Task's ID") @PathVariable(name = "id") long id) {
        taskService.deleteTask(id);
    }
}
