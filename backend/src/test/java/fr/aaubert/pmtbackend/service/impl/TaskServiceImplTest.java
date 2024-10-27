package fr.aaubert.pmtbackend.service.impl;


import fr.aaubert.pmtbackend.model.Project;
import fr.aaubert.pmtbackend.model.Task;
import fr.aaubert.pmtbackend.model.TaskMember;
import fr.aaubert.pmtbackend.model.User;
import fr.aaubert.pmtbackend.repository.ProjectRepository;
import fr.aaubert.pmtbackend.repository.TaskMemberRepository;
import fr.aaubert.pmtbackend.repository.TaskRepository;
import fr.aaubert.pmtbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskServiceImpl;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMemberRepository taskMemberRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_shouldReturnTask_whenProjectExists() {
        // Arrange
        Project project = new Project();
        project.setProjectId(1L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Task task = new Task();
        task.setName("New Task");
        task.setDescription("Task description");
        task.setDueDate(new Date());
        task.setPriority("High");
        task.setStatus("Pending");
        task.setProject(project);

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Act
        Task createdTask = taskServiceImpl.createTask(task, 1L);

        // Assert
        assertNotNull(createdTask);
        assertEquals("New Task", createdTask.getName());
        assertEquals("High", createdTask.getPriority());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void assignTaskToUser_shouldReturnTaskMember_whenTaskAndUserExist() {
        // Arrange
        Task task = new Task();
        task.setId(1L);

        User user = new User();
        user.setUserId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        TaskMember taskMember = new TaskMember();
        taskMember.setTask(task);
        taskMember.setUser(user);

        when(taskMemberRepository.save(any(TaskMember.class))).thenReturn(taskMember);

        // Act
        TaskMember assignedTaskMember = taskServiceImpl.assignTaskToUser(1L, 1L);

        // Assert
        assertNotNull(assignedTaskMember);
        assertEquals(task, assignedTaskMember.getTask());
        assertEquals(user, assignedTaskMember.getUser());
        verify(taskMemberRepository, times(1)).save(any(TaskMember.class));
    }

    @Test
    void createTask_shouldThrowException_whenProjectDoesNotExist() {
        // Arrange

        Task task = new Task();
        task.setName("New Task");
        task.setDescription("Task description");
        task.setDueDate(new Date());
        task.setPriority("High");
        task.setStatus("Pending");

        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> taskServiceImpl.createTask(task, 1L));
    }

    @Test
    void assignTaskToUser_shouldThrowException_whenTaskOrUserDoesNotExist() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> taskServiceImpl.assignTaskToUser(1L, 1L));
    }

    @Test
    void getTasksByProjectId_shouldReturnTasks_whenProjectHasTasks() {
        // Arrange
        Long projectId = 1L;
        Task task1 = new Task();
        task1.setId(1L);
        task1.setName("Task 1");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setName("Task 2");

        List<Task> tasks = List.of(task1, task2);

        when(taskRepository.findByProjectId(projectId)).thenReturn(tasks);

        // Act
        List<Task> result = taskServiceImpl.getTasksByProjectId(projectId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findByProjectId(projectId);
    }

}
