package fr.aaubert.pmtbackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectRequestTest {

    @Mock
    private Project mockProject;

    private ProjectRequest projectRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize ProjectRequest with a mock Project and userId
        projectRequest = new ProjectRequest();
        projectRequest.setProject(mockProject);
        projectRequest.setUserId(1L);
    }

    @Test
    public void testGetProject() {
        // Verify that getProject returns the correct Project object
        assertEquals(mockProject, projectRequest.getProject());
    }

    @Test
    public void testSetProject() {
        // Create a new mock project and set it in the ProjectRequest
        Project newMockProject = new Project();
        projectRequest.setProject(newMockProject);

        // Verify that the project has been updated
        assertEquals(newMockProject, projectRequest.getProject());
    }

    @Test
    public void testGetUserId() {
        // Verify that getUserId returns the correct user ID
        assertEquals(1L, projectRequest.getUserId());
    }

    @Test
    public void testSetUserId() {
        // Set a new userId and verify the change
        projectRequest.setUserId(2L);
        assertEquals(2L, projectRequest.getUserId());
    }
}
