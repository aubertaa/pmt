package fr.aaubert.pmtbackend.service.impl;

import fr.aaubert.pmtbackend.exceptions.EntityDontExistException;
import fr.aaubert.pmtbackend.model.*;
import fr.aaubert.pmtbackend.repository.ProjectMemberRepository;
import fr.aaubert.pmtbackend.repository.ProjectRepository;
import fr.aaubert.pmtbackend.repository.UserRepository;
import fr.aaubert.pmtbackend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//import java.util.List;
//import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    private UserRepository userRepository;

    public Long saveProjectWithOwner(Project project, Long userId) {

        // check if user exists
        User user = userRepository.findById(userId)
                .orElseThrow(EntityDontExistException::new);

        // save the project
        Project savedProject = projectRepository.save(project);

        // Create and save the association as OWNER
        ProjectMember member  = new ProjectMember();
        ProjectMemberId memberId = new ProjectMemberId();
        memberId.setUserId(user.getUserId());
        memberId.setProjectId(savedProject.getProjectId());
        member.setId(memberId);
        member.setUser(user);
        member.setProject(savedProject);
        member.setRole(UserRole.OWNER);

        projectMemberRepository.save(member);

        //Return the saved project's ID
        return savedProject.getProjectId();
    }

    @Override
    public Project getProjectByProjectName(String projectName) {

        Optional<Project> project = Optional.ofNullable(projectRepository.findByProjectname(projectName));

        // On trouve le project
        if(project.isPresent()) {
            return project.get();
        }

        //sinon on renvoie une exception
        throw new EntityDontExistException();

    }

    @Override
    public List<Project> getProjectsByUserId(Long userId) {

        return projectMemberRepository.getProjectsByUserId(userId);

    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

}
