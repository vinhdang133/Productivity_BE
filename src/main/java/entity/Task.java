package entity;


import entity.enums.Priority;
import entity.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "tasks")

public class Task {


    @Id
    private long id;





    // ── Relationships ──────────────────────────────────────────

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_task_user")
    )
    private Account user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "project_id",                // nullable — task không bắt buộc có project
            foreignKey = @ForeignKey(name = "fk_task_project")
    )
    private Project project;

    // Self-reference: task có thể là subtask của task khác
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "parent_task_id",
            foreignKey = @ForeignKey(name = "fk_task_parent")
    )
    private Task parentTask;

    @OneToMany(
            mappedBy = "parentTask",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Task> subTasks = new ArrayList<>();


    // Many-to-many với Label qua bảng task_labels
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "task_labels",
            joinColumns        = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id"),
            foreignKey        = @ForeignKey(name = "fk_tl_task"),
            inverseForeignKey = @ForeignKey(name = "fk_tl_label")
    )
    @Builder.Default
    private List<Label> labels = new ArrayList<>();


    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    @Builder.Default
    private List<FocusSession> focusSessions = new ArrayList<>();



    // ── Core fields ────────────────────────────────────────────

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Builder.Default
    private TaskStatus status = TaskStatus.TO_DO;


    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Priority priority;

    // ── Time tracking ──────────────────────────────────────────

    @Column(name = "estimated_minutes")
    private Integer estimatedMinutes;

    @Column(name = "actual_minutes")
    @Builder.Default
    private Integer actualMinutes = 0;

    @Column(name = "due_date")
    private LocalDateTime dueDate;


    // ── Recurrence ─────────────────────────────────────────────

    @Column(name = "is_recurring")
    @Builder.Default
    private boolean recurring = false;

    @Column(name = "recurrence_rule", length = 255)
    private String recurrenceRule;

    // ── Ordering ───────────────────────────────────────────────

    @Column(name = "sort_order")
    @Builder.Default
    private Integer sortOrder = 0;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;




    // ── Helper methods ─────────────────────────────────────────

    public void markCompleted() {
        this.status = TaskStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }

    public void reopen() {
        this.status = TaskStatus.IN_PROGRESS;
        this.completedAt = null;
    }

    public boolean isOverdue() {
        return dueDate != null
                && !isCompleted()
                && dueDate.isBefore(LocalDateTime.now());
    }

    public boolean isCompleted() {
        return status == TaskStatus.COMPLETED;
    }

    public void addFocusMinutes(int minutes) {
        this.actualMinutes = (actualMinutes == null ? 0 : actualMinutes) + minutes;
    }
}




