package hexlet.code.app.service;

import hexlet.code.app.dto.TaskDto;
import hexlet.code.app.model.Task;
import hexlet.code.app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private StatusServiceImpl statusService;
    @Override
    public Task fineTaskById(long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task createNewTask(TaskDto taskDto) {
        Task newTask = new Task();
        newTask.setName(taskDto.getName());
        newTask.setDescription(taskDto.getDescription());
        newTask.setAuthor(userService.getCurrentUser());

        long taskStatusId = taskDto.getTaskStatusId();
        newTask.setTaskStatus(statusService.findStatusById(taskStatusId));

        long executorId = taskDto.getExecutorId();
        try {
            newTask.setExecutor(userService.findUserByUserId(executorId));
        } catch (Exception e) {
            System.out.println("There is no User with executorId = " + executorId);
        }

        return taskRepository.save(newTask);
    }

    @Override
    public Task updateTask(long id, TaskDto taskDto) {
        Task currentTask = taskRepository.findById(id).orElseThrow();
        currentTask.setName(taskDto.getName());
        currentTask.setDescription(taskDto.getDescription());
        currentTask.setTaskStatus(statusService.findStatusById(taskDto.getTaskStatusId()));
        currentTask.setExecutor(userService.findUserByUserId(taskDto.getExecutorId()));
        currentTask.setCreatedAt(new Date());
        return taskRepository.save(currentTask);
    }

    @Override
    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }
}
