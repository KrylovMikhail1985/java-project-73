package hexlet.code.app.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TaskDto {
    @Size(min = 1)
    @NotNull
    private String name;
    private String description;
    @NotNull
    private long taskStatusId;
    @NotNull
    private long executorId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTaskStatusId() {
        return taskStatusId;
    }

    public void setTaskStatusId(long taskStatusId) {
        this.taskStatusId = taskStatusId;
    }

    public long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(long executorId) {
        this.executorId = executorId;
    }
}
