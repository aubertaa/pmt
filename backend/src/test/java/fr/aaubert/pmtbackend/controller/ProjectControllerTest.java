package fr.aaubert.pmtbackend.controller;

import fr.aaubert.pmtbackend.model.Project;
import fr.aaubert.pmtbackend.model.ProjectRequest;
import fr.aaubert.pmtbackend.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProject() {
        // Arrange
        Project project = new Project();
        Long userId = 1L;
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setProject(project);
        projectRequest.setUserId(userId);

        when(projectService.saveProjectWithOwner(project, userId)).thenReturn(1L);

        // Act
        Long response = projectController.create(projectRequest);

        // Assert
        assertEquals(1L, response);
        verify(projectService, times(1)).saveProjectWithOwner(project, userId);
    }

    @Test
    public void testGetProjectByProjectName() {
        // Arrange
        String projectName = "Test Project";
        Project project = new Project();
        when(projectService.getProjectByProjectName(projectName)).thenReturn(project);

        // Act
        Project response = projectController.getProjectByProjectName(projectName);

        // Assert
        assertEquals(project, response);
        verify(projectService, times(1)).getProjectByProjectName(projectName);
    }

    @Test
    public void testGetProjectsByUserId() {
        // Arrange
        Long userId = 1L;
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());
        when(projectService.getProjectsByUserId(userId)).thenReturn(projects);

        // Act
        List<Project> response = projectController.getProjectsByUserId(userId);

        // Assert
        assertEquals(projects, response);
        verify(projectService, times(1)).getProjectsByUserId(userId);
    }
}
