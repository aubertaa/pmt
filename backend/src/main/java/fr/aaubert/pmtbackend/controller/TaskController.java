package fr.aaubert.pmtbackend.controller;


import fr.aaubert.pmtbackend.model.*;
import fr.aaubert.pmtbackend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


    @GetMapping("/tasks")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<Map<String, Object>>> getTasks() {
        List<Map<String, Object>> taskMaps = taskService.getTasks().stream()
                .map(task -> {
                    Map<String, Object> taskMap = new HashMap<>();
                    taskMap.put("id", task.getId());
                    taskMap.put("name", task.getName());
                    taskMap.put("description", task.getDescription());
                    taskMap.put("dueDate", task.getDueDate());
                    taskMap.put("priority", task.getPriority());
                    taskMap.put("status", task.getStatus());
                    taskMap.put("projectId", task.getProject().getProjectId()); // Adding projectId
                    return taskMap;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskMaps);
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
