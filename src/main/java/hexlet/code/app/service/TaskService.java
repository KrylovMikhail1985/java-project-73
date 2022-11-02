package hexlet.code.app.service;

import hexlet.code.app.dto.TaskDto;
import hexlet.code.app.model.Task;

import java.util.List;

public interface TaskService {
    Task fineTaskById(long id);
    List<Task> findAllTasks();
    Task createNewTask(TaskDto taskDto);
    Task updateTask(long id, TaskDto taskDto);
    void deleteTask(long id);
}
