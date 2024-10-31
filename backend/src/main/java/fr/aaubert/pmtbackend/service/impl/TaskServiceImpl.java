package fr.aaubert.pmtbackend.service.impl;

import fr.aaubert.pmtbackend.model.*;
import fr.aaubert.pmtbackend.repository.ProjectRepository;
import fr.aaubert.pmtbackend.repository.TaskMemberRepository;
import fr.aaubert.pmtbackend.repository.TaskRepository;
import fr.aaubert.pmtbackend.repository.TasksHistoryRepository;
import fr.aaubert.pmtbackend.repository.UserRepository;
import fr.aaubert.pmtbackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TasksHistoryRepository tasksHistoryRepository;

    @Autowired
    private TaskMemberRepository taskMemberRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    // Méthode pour créer une tâche dans un projet
    @Override
    public Task createTask(Task task, Long projectId, Long userId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            task.setProject(project.get());
            Task savedTask = taskRepository.save(task);

            //record history
            recordTaskModification(savedTask, "CREATE", null, task.toString(), userId);

            return savedTask;
        }
        throw new RuntimeException("Project not found");
    }

    // Méthode pour assigner une tâche à un membre spécifique
    @Override
    public TaskMember assignTaskToUser(Long taskId, Long userId, Long authorId) {
        Optional<Task> task = taskRepository.findById(taskId);
        Optional<User> user = userRepository.findById(userId);

        if (task.isPresent() && user.isPresent()) {
            TaskMember taskMember = taskMemberRepository.findByTaskId(taskId);

            String previousAssignee = null;
            if (taskMember != null) {
                // Task already has a user assigned, update to new user
                previousAssignee = taskMember.getUser().getUserName().toString();
                taskMember.setUser(user.get());
            } else {
                // No TaskMember exists, create a new one
                taskMember = new TaskMember();
                taskMember.setTask(task.get());
                taskMember.setUser(user.get());
            }
            TaskMember savedMember = taskMemberRepository.save(taskMember);

                //record history
                recordTaskModification(task.get(), "ASSIGN", previousAssignee != null ? "Assigned to " + previousAssignee : "Not assigned" , "Assigned to " + user.get().getUserName(), authorId);

            return taskMemberRepository.save(taskMember);

        }
        throw new RuntimeException("Task or User not found");
    }


    // Méthode pour récupérer les tâches d'un projet spécifique
    @Override
    public List<Task> getTasksByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @Override
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Long getTaskMemberUser(Long task_id) {
        return taskMemberRepository.getMemberUserIdByTaskId(task_id);
    }

    @Transactional
    @Override
    public Task updateTask(Task task, Long projectId, Long userId) {

        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            task.setProject(project.get());

            Task existingTask = taskRepository.findById(task.getId()).orElseThrow();

            // Capture old and new values for changes
            String oldValue = existingTask.toString();
            String newValue = task.toString();

            Task updatedTask = taskRepository.save(task);

            //record history
            recordTaskModification(updatedTask, "UPDATE", oldValue, newValue, userId);

            return updatedTask;
        }
        throw new RuntimeException("Project not found");

    }

    @Override
    public void recordTaskModification(Task task, String modificationType, String oldValue, String newValue, Long modifiedBy) {
        TasksHistory history = new TasksHistory();
        history.setTask(task);
        history.setModificationType(modificationType);
        history.setOldValue(oldValue);
        history.setNewValue(newValue);
        history.setModifiedBy(modifiedBy);
        history.setDateModified(new Date());
        tasksHistoryRepository.save(history);
    }

    @Override
    public List<TasksHistory> getTasksHistory() {
        return tasksHistoryRepository.findAll();
    }

}
