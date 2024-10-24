package fr.aaubert.pmtbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "projects", uniqueConstraints = @UniqueConstraint(columnNames = "projectName"))
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long projectId;

    @NotNull
    private String projectName;

    private String description;

    private Date startDate;

    // Define the relationship with ProjectMember
    @OneToMany(mappedBy = "id.projectId", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ProjectMember> projectMembers;

    // Additional fields, constructors, getters, and setters
}
