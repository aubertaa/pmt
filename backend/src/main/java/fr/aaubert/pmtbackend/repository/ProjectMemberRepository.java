package fr.aaubert.pmtbackend.repository;

import fr.aaubert.pmtbackend.model.Project;
import fr.aaubert.pmtbackend.model.ProjectMember;
import fr.aaubert.pmtbackend.model.ProjectMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {
    @Query("SELECT pm.project FROM ProjectMember pm WHERE pm.user.id = :userId")
    List<Project> getProjectsByUserId(Long userId);  // Query all ProjectMembers for the user

    @Query("SELECT pm.user FROM ProjectMember pm WHERE pm.project.id = :projectId AND pm.user.id = :userId")
    boolean existsByUserIdAndProjectId(Long userId, Long projectId); // check if user is already a member of the project

}
