package com.productivity.web.entity;

import com.productivity.web.entity.enums.SessionStatus;
import com.productivity.web.entity.enums.SessionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "focus_sessions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FocusSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_focus_session_user")
    )
    private Account user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "task_id",
            foreignKey = @ForeignKey(name = "fk_focus_session_task")
    )
    private Task task;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "session_label_id",
            foreignKey = @ForeignKey(name = "fk_focus_session_label")
    )
    private Label sessionLabel;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_type", length = 20, nullable = false)
    @Builder.Default

    private SessionType sessionType = SessionType.FOCUS;




    @Column(name = "planned_duration_minutes", nullable = false)
    @Builder.Default
    private Integer plannedDurationMinutes = 25;

    @Column(name = "actual_duration_minutes") // null khi đang chạy
    private Integer actualDurationMinutes;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at")              // null khi đang chạy
    private LocalDateTime endedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private SessionStatus status = SessionStatus.RUNNING;

    @Column(columnDefinition = "TEXT")
    private String notes;

}
