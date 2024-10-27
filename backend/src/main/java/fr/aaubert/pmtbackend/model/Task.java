package fr.aaubert.pmtbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class Task {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    private String priority; // e.g., "High", "Medium", "Low"

    private String status; // e.g., "Pending", "Completed", "In Progress"

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

}
