package fr.aaubert.pmtbackend.repository;

import fr.aaubert.pmtbackend.model.TasksHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksHistoryRepository extends JpaRepository<TasksHistory, Long> {
}
