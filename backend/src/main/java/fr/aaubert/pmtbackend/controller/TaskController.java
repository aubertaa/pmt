package fr.aaubert.pmtbackend.controller;


import fr.aaubert.pmtbackend.model.*;
import fr.aaubert.pmtbackend.service.EmailService;
import fr.aaubert.pmtbackend.service.TaskService;
import fr.aaubert.pmtbackend.service.UserService;
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
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    // Endpoint pour créer une tâche dans un projet
    @PostMapping("/task")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Task> createTask(@RequestBody @Valid TaskRequest taskRequest) {
        Task task = taskService.createTask(taskRequest.getTask(), taskRequest.getProjectId(), taskRequest.getUserId());
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/task")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Task> updateTask(@RequestBody @Valid TaskRequest taskRequest) {
        Task updatedTask = taskService.updateTask(taskRequest.getTask(), taskRequest.getProjectId(), taskRequest.getUserId());
        return ResponseEntity.ok(updatedTask);
    }

    // Endpoint pour assigner une tâche à un membre
    @PostMapping("/task/assign")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<TaskMember> assignTaskToUser(@RequestParam("taskId") Long taskId, @RequestParam("userId") Long userId, @RequestParam("authorId") Long authorId) {
        TaskMember taskMember = taskService.assignTaskToUser(taskId, userId, authorId);

        User user = userService.getUserByUserId(userId);
        String subject = "Task assigned";
        String body = "Task " + taskId + " has been assigned to " + user.getUserName() + ". Please check your PMT dashboard for more details.";

        //get all users email having notifications = true
        List<String> destEmails = userService.getAllUsersEmailHavingNotificationsTrue();
        for (String toEmail : destEmails) {
            emailService.sendEmail(toEmail, subject, body);
        }

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
                    taskMap.put("assignedTo", taskService.getTaskMemberUser(task.getId())); // Adding taskMemberUserId

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

    @GetMapping("/tasks/history")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TasksHistory> getTasksHistory() {
        return taskService.getTasksHistory();
    }
}
