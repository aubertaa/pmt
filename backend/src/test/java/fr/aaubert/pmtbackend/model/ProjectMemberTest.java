package fr.aaubert.pmtbackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProjectMemberTest {

    @Mock
    private User mockUser;

    @Mock
    private Project mockProject;

    @InjectMocks
    private ProjectMember projectMember;

    private ProjectMemberId projectMemberId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up the mock ID
        projectMemberId = new ProjectMemberId();
        projectMemberId.setUserId(1L);
        projectMemberId.setProjectId(1L);

        // Initialize the ProjectMember with mock data
        projectMember = new ProjectMember();
        projectMember.setId(projectMemberId);
        projectMember.setUser(mockUser);
        projectMember.setProject(mockProject);
        projectMember.setRole(UserRole.OWNER);

        // Set up User and Project mock behavior if necessary
        when(mockUser.getUserId()).thenReturn(1L);
        when(mockProject.getProjectId()).thenReturn(1L);
    }

    @Test
    public void testProjectMemberInitialization() {
        // Verify that fields are set as expected
        assertEquals(projectMemberId, projectMember.getId());
        assertEquals(mockUser, projectMember.getUser());
        assertEquals(mockProject, projectMember.getProject());
        assertEquals(UserRole.OWNER, projectMember.getRole());
    }

    @Test
    public void testUserMappingInProjectMember() {
        // Check if ProjectMember is associated with the correct User ID
        assertEquals(1L, projectMember.getUser().getUserId());
    }

    @Test
    public void testProjectMappingInProjectMember() {
        // Check if ProjectMember is associated with the correct Project ID
        assertEquals(1L, projectMember.getProject().getProjectId());
    }

    @Test
    public void testRoleAssignment() {
        // Verify role assignment
        assertEquals(UserRole.OWNER, projectMember.getRole());

        // Change role and verify update
        projectMember.setRole(UserRole.CONTRIBUTOR);
        assertEquals(UserRole.CONTRIBUTOR, projectMember.getRole());
    }
}
