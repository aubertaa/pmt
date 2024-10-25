package fr.aaubert.pmtbackend.controller;

import fr.aaubert.pmtbackend.model.Project;
import fr.aaubert.pmtbackend.model.ProjectRequest;
import fr.aaubert.pmtbackend.service.ProjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS})

public class ProjectController {
    //will have here API endpoints for user management

    @Autowired
    private ProjectService projectService;

    @PostMapping("/project")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Long create(@RequestBody @Valid ProjectRequest projectRequest) {
        return projectService.saveProjectWithOwner(projectRequest.getProject(), projectRequest.getUserId());
    }

    @GetMapping("/project")
    @ResponseStatus(code = HttpStatus.OK)
    public Project getProjectByProjectName(@Param("projectName") String projectName) {
        return projectService.getProjectByProjectName(projectName);
    }

    @GetMapping("/projects")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<Map<String, Object>>> getProjects(@Param("userId") Long userId) {
        List<Project> projects = projectService.getProjectsByUserId(userId);

        // Build response map with projectId included
        List<Map<String, Object>> response = projects.stream().map(project -> {
            Map<String, Object> projectMap = new HashMap<>();
            projectMap.put("id", project.getProjectId());
            projectMap.put("projectName", project.getProjectName());
            projectMap.put("description", project.getDescription());
            projectMap.put("startDate", project.getStartDate());
            return projectMap;
        }).toList();

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/project")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteProject(@RequestParam("id") String projectId) {
        projectService.deleteProject(Long.valueOf(projectId));
    }

}
