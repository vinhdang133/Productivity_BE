package entity;

import entity.enums.SessionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
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


    @Enumerated(EnumType.STRING)
    @Column(name = "session_type", length = 20)
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

    @Column(name = "was_completed")
    @Builder.Default
    private Boolean completed = false;

    @Column(name = "was_interrupted")
    @Builder.Default
    private Boolean interrupted = false;

    @Column(columnDefinition = "TEXT")
    private String notes;

}
