package entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Account owner_Id;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
