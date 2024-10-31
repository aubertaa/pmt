package fr.aaubert.pmtbackend.controller;


import fr.aaubert.pmtbackend.model.*;
import fr.aaubert.pmtbackend.service.EmailService;
import fr.aaubert.pmtbackend.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.aaubert.pmtbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmailService emailService;


    @Test
    void createTask_shouldReturnTask_whenValidRequest() throws Exception {

        Task task = new Task();
        task.setId(1L);
        task.setName("New Task");
        task.setDescription("Task description");
        task.setDueDate(new Date());
        task.setPriority(TaskPriority.HIGH);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTask(task);
        taskRequest.setProjectId(1L);
        taskRequest.setUserId(1L);


        when(taskService.createTask(any(Task.class), eq(1L), eq(1L))).thenReturn(task);

        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Task"));
    }

    @Test
    void assignTaskToUser_shouldReturnTaskMember_whenValidRequest() throws Exception {
        // Arrange

        User user = new User();
        user.setUserId(1L);
        user.setUserName("User 1");
        user.setEmail("mail@mail");

        TaskMember taskMember = new TaskMember();
        taskMember.setId(1L);
        taskMember.setUser(user);

        when(taskService.assignTaskToUser(1L, 2L, 3L)).thenReturn(taskMember);
        when(userService.getUserByUserId(1L)).thenReturn(user);

        // Act & Assert
        mockMvc.perform(post("/api/task/assign?taskId=1&userId=1&authorId=1&notifications=true"))
                .andExpect(status().isOk());
    }

    @Test
    void getTasks_shouldReturnTasksList_whenValidRequest() throws Exception {

        Project project = new Project();
        project.setProjectId(1L);
        project.setProjectName("Project 1");
        project.setDescription("Project description");


        Task task1 = new Task();
        task1.setId(1L);
        task1.setName("Task 1");
        task1.setProject(project);
        Task task2 = new Task();
        task2.setId(2L);
        task2.setName("Task 2");
        task2.setProject(project);

        List<Task> tasks = List.of(task1, task2);

        when(taskService.getTasks()).thenReturn(tasks);
        when(taskService.getTaskMemberUser(2L)).thenReturn(8L);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Task 1"))
                .andExpect(jsonPath("$[0].projectId").value(1L))
                .andExpect(jsonPath("$[0].assignedTo").value(0L))
                .andExpect(jsonPath("$[1].name").value("Task 2"))
                .andExpect(jsonPath("$[1].projectId").value(1L))
                .andExpect(jsonPath("$[1].assignedTo").value(8L));
    }

    @Test
    void getAllPriorities_shouldReturnPrioritiesList_whenValidRequest() throws Exception {
        mockMvc.perform(get("/api/priorities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getAllStatuses_shouldReturnStatusesList_whenValidRequest() throws Exception {
        mockMvc.perform(get("/api/statuses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void updateTask_shouldReturnTask_whenValidRequest() throws Exception {

        Task task = new Task();
        task.setId(1L);
        task.setName("New Task");
        task.setDescription("Task description");
        task.setDueDate(new Date());
        task.setPriority(TaskPriority.HIGH);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTask(task);
        taskRequest.setProjectId(1L);
        taskRequest.setUserId(1L);

        when(taskService.updateTask(any(Task.class), eq(1L),eq(1L) )).thenReturn(task);

        mockMvc.perform(patch("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Task"));
    }

}