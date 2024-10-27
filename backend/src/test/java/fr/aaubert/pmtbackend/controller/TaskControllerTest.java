package fr.aaubert.pmtbackend.controller;


import fr.aaubert.pmtbackend.model.Task;
import fr.aaubert.pmtbackend.model.TaskMember;
import fr.aaubert.pmtbackend.model.TaskPriority;
import fr.aaubert.pmtbackend.model.TaskRequest;
import fr.aaubert.pmtbackend.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

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


        when(taskService.createTask(any(Task.class), eq(1L))).thenReturn(task);

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
        TaskMember taskMember = new TaskMember();
        taskMember.setId(1L);

        when(taskService.assignTaskToUser(1L, 1L)).thenReturn(taskMember);

        // Act & Assert
        mockMvc.perform(post("/api/task/assign?taskId=1&userId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getTasksByProjectId_shouldReturnTasksList_whenValidRequest() throws Exception {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setName("Task 1");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setName("Task 2");

        List<Task> tasks = List.of(task1, task2);

        when(taskService.getTasksByProjectId(1L)).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks?projectId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Task 1"))
                .andExpect(jsonPath("$[1].name").value("Task 2"));
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

}
