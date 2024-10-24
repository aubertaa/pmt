package fr.aaubert.pmtbackend.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
@EqualsAndHashCode
public class ProjectMemberId implements Serializable {

    // Getters and setters
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "project_id")
    private Long projectId;

    // Default constructor
    public ProjectMemberId() {}

    public ProjectMemberId(Long userId, Long projectId) {
        this.userId = userId;
        this.projectId = projectId;
    }


}
