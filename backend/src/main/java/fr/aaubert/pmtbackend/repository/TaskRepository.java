package fr.aaubert.pmtbackend.repository;


import fr.aaubert.pmtbackend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("""
                 FROM Task t
                 WHERE t.project.id = :projectId
            """)
    List<Task> findByProjectId(Long projectId);

}
