package fr.aaubert.pmtbackend.service.impl;

import fr.aaubert.pmtbackend.exceptions.EntityDontExistException;
import fr.aaubert.pmtbackend.model.*;
import fr.aaubert.pmtbackend.repository.ProjectMemberRepository;
import fr.aaubert.pmtbackend.repository.ProjectRepository;
import fr.aaubert.pmtbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveProjectWithOwner_Success() {
        // Arrange
        Long userId = 1L;
        Project project = new Project();
        project.setProjectName("Test Project");

        User user = new User();
        user.setUserId(userId);

        Project savedProject = new Project();
        savedProject.setProjectId(1L);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(projectRepository.save(project)).thenReturn(savedProject);

        // Act
        Long result = projectService.saveProjectWithOwner(project, userId);

        // Assert
        assertEquals(savedProject.getProjectId(), result);
        verify(userRepository, times(1)).findById(userId);
        verify(projectRepository, times(1)).save(project);
        verify(projectMemberRepository, times(1)).save(any(ProjectMember.class));
    }

    @Test
    public void testSaveProjectWithOwner_UserNotFound() {
        // Arrange
        Long userId = 1L;
        Project project = new Project();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> projectService.saveProjectWithOwner(project, userId));
        verify(userRepository, times(1)).findById(userId);
        verify(projectRepository, never()).save(project);
        verify(projectMemberRepository, never()).save(any(ProjectMember.class));
    }

    @Test
    public void testGetProjectByProjectName_Success() {
        // Arrange
        String projectName = "Test Project";
        Project project = new Project();
        project.setProjectName(projectName);

        when(projectRepository.findByProjectname(projectName)).thenReturn(project);

        // Act
        Project result = projectService.getProjectByProjectName(projectName);

        // Assert
        assertEquals(project, result);
        verify(projectRepository, times(1)).findByProjectname(projectName);
    }

    @Test
    public void testGetProjectByProjectName_ProjectNotFound() {
        // Arrange
        String projectName = "Nonexistent Project";
        when(projectRepository.findByProjectname(projectName)).thenReturn(null);

        // Act & Assert
        assertThrows(EntityDontExistException.class, () -> projectService.getProjectByProjectName(projectName));
        verify(projectRepository, times(1)).findByProjectname(projectName);
    }

    @Test
    public void testGetProjectsByUserId_Success() {
        // Arrange
        Long userId = 1L;
        Project project1 = new Project();
        Project project2 = new Project();
        List<Project> projects = List.of(project1, project2);

        when(projectMemberRepository.getProjectsByUserId(userId)).thenReturn(projects);

        // Act
        List<Project> result = projectService.getProjectsByUserId(userId);

        // Assert
        assertEquals(projects, result);
        verify(projectMemberRepository, times(1)).getProjectsByUserId(userId);
    }

    @Test
    public void testDeleteProject_Success() {
        // Arrange
        Long projectId = 1L;

        // Act
        projectService.deleteProject(projectId);

        // Assert
        verify(projectRepository, times(1)).deleteById(projectId);
    }
}
