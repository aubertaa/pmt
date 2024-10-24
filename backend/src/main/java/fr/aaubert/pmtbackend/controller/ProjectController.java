package fr.aaubert.pmtbackend.controller;
import fr.aaubert.pmtbackend.model.Project;
import fr.aaubert.pmtbackend.model.ProjectRequest;
import fr.aaubert.pmtbackend.service.ProjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"})
public class ProjectController {
    //will have here API endpoints for user management

    @Autowired
    private ProjectService projectService;

    @PostMapping("/project")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Long create(@RequestBody @Valid ProjectRequest projectRequest){
        return projectService.saveProjectWithOwner(projectRequest.getProject(), projectRequest.getUserId());
    }

    @GetMapping("/project")
    @ResponseStatus(code = HttpStatus.OK)
    public Project getProjectByProjectName(@Param("projectName") String projectName){
        return projectService.getProjectByProjectName(projectName);
    }

    @GetMapping("/projects/user/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Project> getProjectsByUserId(@PathVariable Long userId){
        return projectService.getProjectsByUserId(userId);
    }

}
