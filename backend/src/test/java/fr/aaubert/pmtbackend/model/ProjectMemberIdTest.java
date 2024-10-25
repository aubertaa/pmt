package fr.aaubert.pmtbackend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectMemberIdTest {

    @Test
    void ProjectMemberIdConstructor() {
        ProjectMemberId projectMemberId = new ProjectMemberId(1L, 2L);
        assertNotNull(projectMemberId);
        assertEquals(1L, projectMemberId.getUserId());
        assertEquals(2L, projectMemberId.getProjectId());
    }

    @Test
    void ProjectMemberIdDefaultConstructor() {
        ProjectMemberId projectMemberId = new ProjectMemberId();
        assertNotNull(projectMemberId);
    }

    @Test
    void ProjectMemberIdSetters() {
        ProjectMemberId projectMemberId = new ProjectMemberId();
        projectMemberId.setUserId(1L);
        projectMemberId.setProjectId(2L);
        assertEquals(1L, projectMemberId.getUserId());
        assertEquals(2L, projectMemberId.getProjectId());
    }

    @Test
    void ProjectMemberIdEqualsAndHashCode() {
        ProjectMemberId projectMemberId1 = new ProjectMemberId(1L, 2L);
        ProjectMemberId projectMemberId2 = new ProjectMemberId(1L, 2L);
        assertEquals(projectMemberId1, projectMemberId2);
        assertEquals(projectMemberId1.hashCode(), projectMemberId2.hashCode());
    }

    @Test
    void ProjectMemberIdNotEquals() {
        ProjectMemberId projectMemberId1 = new ProjectMemberId(1L, 2L);
        ProjectMemberId projectMemberId2 = new ProjectMemberId(2L, 1L);
        assertNotEquals(projectMemberId1, projectMemberId2);
    }
}