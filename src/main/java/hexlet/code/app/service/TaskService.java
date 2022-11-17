package hexlet.code.app.service;

import com.querydsl.core.types.Predicate;
import hexlet.code.app.dto.TaskDto;
import hexlet.code.app.model.Task;

public interface TaskService {
    Task fineTaskById(long id);
    Iterable<Task> findAllTasks(Predicate predicate);
    Task createNewTask(TaskDto taskDto);
    Task updateTask(long id, TaskDto taskDto);
    void deleteTask(long id);
}
