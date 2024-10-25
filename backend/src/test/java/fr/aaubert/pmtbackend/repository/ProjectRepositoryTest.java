package fr.aaubert.pmtbackend.repository;

import fr.aaubert.pmtbackend.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProjectRepositoryTest {

    @Mock
    private ProjectRepository projectRepository;

    private Project project;

    @BeforeEach
    void setUp() throws ParseException {
        project = new Project();
        project.setProjectName("testproject");
        project.setDescription("testdescr");
        project.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-01"));
    }

    @Test
    public void testFindByProjectName() {
        when(projectRepository.findByProjectname("testproject")).thenReturn(project);

        Project foundProject = projectRepository.findByProjectname("testproject");

        assertNotNull(foundProject);
        assertEquals("testproject", foundProject.getProjectName());
        verify(projectRepository, times(1)).findByProjectname("testproject");
    }

}
