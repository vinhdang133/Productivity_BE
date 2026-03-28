package entity;


import entity.enums.Priority;
import entity.enums.TaskStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")

public class Task {


    @Id
    private long id;

    private String title;

    private String description;

    private TaskStatus status;

    private Priority priority;

    @ManyToOne
    private Project project;

    private Account createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;



}
