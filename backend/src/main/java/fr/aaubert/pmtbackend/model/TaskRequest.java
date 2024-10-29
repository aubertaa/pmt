package fr.aaubert.pmtbackend.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskRequest {

    // Getters and Setters
    private Task task;
    private Long projectId;
    private Long userId;

}
