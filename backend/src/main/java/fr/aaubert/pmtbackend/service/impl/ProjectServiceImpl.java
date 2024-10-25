package fr.aaubert.pmtbackend.service.impl;

import fr.aaubert.pmtbackend.exceptions.EntityDontExistException;
import fr.aaubert.pmtbackend.model.*;
import fr.aaubert.pmtbackend.repository.ProjectMemberRepository;
import fr.aaubert.pmtbackend.repository.ProjectRepository;
import fr.aaubert.pmtbackend.repository.UserRepository;
import fr.aaubert.pmtbackend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    private UserRepository userRepository;

    public Long saveProjectWithOwner(Project project, Long userId) {
        try {
            // Check if user exists
            User user = userRepository.findById(userId)
                    .orElseThrow(EntityDontExistException::new);

            // Save the project
            Project savedProject = projectRepository.save(project);

            // Create and save the association as OWNER
            ProjectMember member = new ProjectMember();
            ProjectMemberId memberId = new ProjectMemberId();
            memberId.setUserId(user.getUserId());
            memberId.setProjectId(savedProject.getProjectId());
            member.setId(memberId);
            member.setUser(user);
            member.setProject(savedProject);
            member.setRole(UserRole.OWNER);

            projectMemberRepository.save(member);

            // Return the saved project's ID
            return savedProject.getProjectId();

        } catch (Exception ex) {

            // Return a 400 Bad Request error
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Project creation failed", ex);
        }
    }

    @Override
    public void addMember(Long projectId, Long userId) {
        try {
            // Check if project exists
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(EntityDontExistException::new);

            // Check if user exists
            User user = userRepository.findById(userId)
                    .orElseThrow(EntityDontExistException::new);

            // Check if user is already a member of the project
            if (projectMemberRepository.existsByUserIdAndProjectId(userId, projectId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already a member of the project");
            }

            // Create and save the association as MEMBER
            ProjectMember member = new ProjectMember();
            ProjectMemberId memberId = new ProjectMemberId();
            memberId.setUserId(user.getUserId());
            memberId.setProjectId(project.getProjectId());
            member.setId(memberId);
            member.setUser(user);
            member.setProject(project);
            member.setRole(UserRole.CONTRIBUTOR);

            projectMemberRepository.save(member);

        } catch (Exception ex) {
            // Return a 400 Bad Request error
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member addition failed", ex);
        }

    }

    @Override
    public void changeRole(Long projectId, Long userId, String new_role) {
        try {
            // Check if project exists
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(EntityDontExistException::new);

            // Check if user exists
            User user = userRepository.findById(userId)
                    .orElseThrow(EntityDontExistException::new);

            // Check if user is a member of the project
            if (!projectMemberRepository.existsByUserIdAndProjectId(userId, projectId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not a member of the project");
            }

            // Find the association and update the role
            ProjectMember member = projectMemberRepository.findById(new ProjectMemberId(userId, projectId))
                    .orElseThrow(EntityDontExistException::new);
            member.setRole(UserRole.valueOf(new_role));

            projectMemberRepository.save(member);

        } catch (Exception ex) {
            // Return a 400 Bad Request error
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role change failed", ex);
        }
    }

    @Override
    public void removeMember(Long projectId, Long userId) {
        try {
            // Check if project exists
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(EntityDontExistException::new);

            // Check if user exists
            User user = userRepository.findById(userId)
                    .orElseThrow(EntityDontExistException::new);

            // Check if user is a member of the project
            if (!projectMemberRepository.existsByUserIdAndProjectId(userId, projectId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not a member of the project");
            }

            // Find the association and delete it
            projectMemberRepository.deleteById(new ProjectMemberId(userId, projectId));

        } catch (Exception ex) {
            // Return a 400 Bad Request error
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member removal failed", ex);
        }
    }

    @Override
    public Project getProjectByProjectName(String projectName) {

        Optional<Project> project = Optional.ofNullable(projectRepository.findByProjectname(projectName));

        // On trouve le project
        if (project.isPresent()) {
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
