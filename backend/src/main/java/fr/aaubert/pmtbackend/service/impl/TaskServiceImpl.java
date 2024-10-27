package fr.aaubert.pmtbackend.service.impl;

import fr.aaubert.pmtbackend.model.Project;
import fr.aaubert.pmtbackend.model.Task;
import fr.aaubert.pmtbackend.model.TaskMember;
import fr.aaubert.pmtbackend.model.User;
import fr.aaubert.pmtbackend.repository.ProjectRepository;
import fr.aaubert.pmtbackend.repository.TaskMemberRepository;
import fr.aaubert.pmtbackend.repository.TaskRepository;
import fr.aaubert.pmtbackend.repository.UserRepository;
import fr.aaubert.pmtbackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMemberRepository taskMemberRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    // Méthode pour créer une tâche dans un projet
    @Override
    public Task createTask(Task task, Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            task.setProject(project.get());
            return taskRepository.save(task);
        }
        throw new RuntimeException("Project not found");
    }

    // Méthode pour assigner une tâche à un membre spécifique
    @Override
    public TaskMember assignTaskToUser(Long taskId, Long userId) {
        Optional<Task> task = taskRepository.findById(taskId);
        Optional<User> user = userRepository.findById(userId);

        if (task.isPresent() && user.isPresent()) {
            TaskMember taskMember = new TaskMember();
            taskMember.setTask(task.get());
            taskMember.setUser(user.get());
            return taskMemberRepository.save(taskMember);
        }
        throw new RuntimeException("Task or User not found");
    }


    // Méthode pour récupérer les tâches d'un projet spécifique
    @Override
    public List<Task> getTasksByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }
}
