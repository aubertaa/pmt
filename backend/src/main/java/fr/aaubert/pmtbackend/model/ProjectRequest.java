package fr.aaubert.pmtbackend.model;

import fr.aaubert.pmtbackend.model.Project;

public class ProjectRequest {

    private Project project;
    private Long userId;  // Owner's user ID

    // Getters and Setters

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
