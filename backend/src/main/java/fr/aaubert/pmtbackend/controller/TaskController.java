package fr.aaubert.pmtbackend.controller;


import fr.aaubert.pmtbackend.model.*;
import fr.aaubert.pmtbackend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(
        origins = {"http://localhost:4200", "http://localhost:4201"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Endpoint pour créer une tâche dans un projet
    @PostMapping("/task")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Task> createTask(@RequestBody @Valid TaskRequest taskRequest) {
        Task task = taskService.createTask(taskRequest.getTask(), taskRequest.getProjectId());
        return ResponseEntity.ok(task);
    }

    // Endpoint pour assigner une tâche à un membre
    @PostMapping("/task/assign")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<TaskMember> assignTaskToUser(@RequestParam("taskId") Long taskId, @RequestParam("userId") Long userId) {
        TaskMember taskMember = taskService.assignTaskToUser(taskId, userId);
        return ResponseEntity.ok(taskMember);
    }


    // Endpoint pour lister les tâches d'un projet spécifique
    @GetMapping("/tasks")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<Task>> getTasksByProjectId(@RequestParam("projectId") Long projectId) {
        List<Task> tasks = taskService.getTasksByProjectId(projectId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/priorities")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TaskPriority> getAllPriorities() {
        return List.of(TaskPriority.values());
    }


    @GetMapping("/statuses")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TaskStatus> getAllStatuses() {
        return List.of(TaskStatus.values());
    }

}
