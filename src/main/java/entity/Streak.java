package entity;


import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "streaks",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_streak_user_date",
                        columnNames = {"user_id", "streak_date"}   // mỗi ngày chỉ có 1 row
                )
        },
        indexes = {
                @Index(name = "idx_streaks_user_date", columnList = "user_id, streak_date")
        }
)
public class Streak {


}
