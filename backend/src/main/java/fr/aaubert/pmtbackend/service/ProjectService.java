package fr.aaubert.pmtbackend.service;

import fr.aaubert.pmtbackend.model.Project;

import java.util.List;

public interface ProjectService {

    List<Project> getProjectsByUserId(Long userId);

    void deleteProject(Long id);

    Project getProjectByProjectName(String projectName);

    Long saveProjectWithOwner(Project project, Long userId);
}
