package fr.aaubert.pmtbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class TasksHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(name = "modified_by", nullable = false)
    private Long modifiedBy;

    @Column(name = "modification_type", length = 20, nullable = false)
    private String modificationType;

    @Column(name = "old_value", length = 255)
    private String oldValue;

    @Column(name = "new_value", length = 255)
    private String newValue;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_modified", nullable = false)
    private Date dateModified;

}
