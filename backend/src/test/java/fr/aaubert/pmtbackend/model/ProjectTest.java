package fr.aaubert.pmtbackend.model;

import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectTest {
    @Test
    void testGetName() {
        Project project = new Project();
        project.setProjectName("leprojet");
        assertEquals("leprojet", project.getProjectName());
    }

    @Test
    void testGetDescription() {
        Project project = new Project();
        project.setDescription("ladescription");
        assertEquals("ladescription", project.getDescription());
    }

    @Test
    void testGetStartDate() {
        Project project = new Project();
        try {
            project.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-01"));
            assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-01"), project.getStartDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void testToString() {
        try {
            Project project = new Project();
            project.setProjectName("leprojet");
            project.setDescription("ladescription");
            project.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-01"));
            assertEquals("Project [projectName=leprojet, description=ladescription, startDate=Fri Jan 01 00:00:00 CET 2021]", project.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void testSetters() {
        try {
            Project project = new Project();
            project.setProjectName("leprojet");
            project.setDescription("ladescription");
            project.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-01"));
            assertEquals("leprojet", project.getProjectName());
            assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-01"), project.getStartDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }
}