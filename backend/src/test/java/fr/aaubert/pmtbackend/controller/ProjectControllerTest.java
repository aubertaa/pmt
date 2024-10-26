package fr.aaubert.pmtbackend.controller;

import fr.aaubert.pmtbackend.model.Project;
import fr.aaubert.pmtbackend.model.ProjectRequest;
import fr.aaubert.pmtbackend.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
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
    public void testDeleteProject() {

        String projectId = "1";

        doNothing().when(projectService).deleteProject(Long.valueOf(projectId));
        projectController.deleteProject(projectId);

        verify(projectService, times(1)).deleteProject(Long.valueOf(projectId));
    }

    @Test
    public void testGetProjects_Success() throws Exception {
        // Mock project data
        Project project1 = new Project();
        project1.setProjectName("name1");
        project1.setDescription("descr1");
        project1.setStartDate(new Date());

        Project project2 = new Project();
        project2.setProjectName("name2");
        project2.setDescription("descr2");
        project2.setStartDate(new Date());

        List<Project> projects = List.of(project1, project2);

        // Mock the service call
        when(projectService.getProjectsByUserId(1L)).thenReturn(projects);

        // Perform GET request and verify response
        mockMvc.perform(get("/api/projects").param("userId", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].projectName", is("name1")))
                .andExpect(jsonPath("$[0].description", is("descr1")))
                .andExpect(jsonPath("$[1].projectName", is("name2")))
                .andExpect(jsonPath("$[1].description", is("descr2")));

        verify(projectService, times(1)).getProjectsByUserId(1L);
    }

    @Test
    public void testGetProjects_NoProjectsFound() throws Exception {
        // Return an empty list
        when(projectService.getProjectsByUserId(1L)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/projects").param("userId", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(projectService, times(1)).getProjectsByUserId(1L);
    }

    @Test
    public void testAddMember() {
        String projectId = "1";
        String userId = "1";
        projectController.addMember(projectId, userId);
        verify(projectService, times(1)).addMember(Long.valueOf(projectId), Long.valueOf(userId));
    }

    @Test
    public void testChangeRole() {
        String projectId = "1";
        String userId = "1";
        String role = "admin";
        projectController.changeRole(projectId, userId, role);
        verify(projectService, times(1)).changeRole(Long.valueOf(projectId), Long.valueOf(userId), role);
    }

    @Test
    public void testRemoveMember() {
        String projectId = "1";
        String userId = "1";
        projectController.removeMember(projectId, userId);
        verify(projectService, times(1)).removeMember(Long.valueOf(projectId), Long.valueOf(userId));
    }


}
