package fr.aaubert.pmtbackend.service;

import fr.aaubert.pmtbackend.model.Task;
import fr.aaubert.pmtbackend.model.TaskMember;

import java.util.List;

public interface TaskService {

    // Méthode pour créer une tâche dans un projet
    Task createTask(Task task, Long projectId);

    // Méthode pour assigner une tâche à un membre spécifique
    TaskMember assignTaskToUser(Long taskId, Long userId);

    // Méthode pour récupérer les tâches d'un projet spécifique
    List<Task> getTasksByProjectId(Long projectId);

    List<Task> getTasks();

    Long getTaskMemberUser(Long task_id);

    Task updateTask(Task task, Long projectId);
}
