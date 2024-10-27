package fr.aaubert.pmtbackend.repository;

import fr.aaubert.pmtbackend.model.TaskMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskMemberRepository extends JpaRepository<TaskMember, Long> {
    // Méthodes de requêtes personnalisées peuvent être ajoutées ici si besoin
}
