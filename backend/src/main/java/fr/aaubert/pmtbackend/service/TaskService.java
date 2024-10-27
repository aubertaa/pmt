package fr.aaubert.pmtbackend.service;

import fr.aaubert.pmtbackend.model.Task;
import fr.aaubert.pmtbackend.model.TaskMember;

import java.util.Date;
import java.util.List;

public interface TaskService {

    // Méthode pour créer une tâche dans un projet
    Task createTask(Task task, Long projectId);

    // Méthode pour assigner une tâche à un membre spécifique
    TaskMember assignTaskToUser(Long taskId, Long userId);

    // Méthode pour récupérer les tâches d'un projet spécifique
    List<Task> getTasksByProjectId(Long projectId);
}
