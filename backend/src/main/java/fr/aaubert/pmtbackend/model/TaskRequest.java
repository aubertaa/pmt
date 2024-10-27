package fr.aaubert.pmtbackend.model;

import java.util.Date;

public class TaskRequest {

    private Task task;
    private Long projectId;

    // Getters and Setters
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
