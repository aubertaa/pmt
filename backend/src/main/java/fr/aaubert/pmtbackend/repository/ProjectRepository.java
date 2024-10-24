package fr.aaubert.pmtbackend.repository;

import fr.aaubert.pmtbackend.model.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
                 FROM Project
                 WHERE projectName = :projectName
            """)
    Project findByProjectname(String projectName);

}
