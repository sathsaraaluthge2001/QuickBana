package com.quickbana.quickbana.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.logging.Level;

@Entity
@Table(name = "user_progress")
@Getter
@Setter
public class UserProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "level_id", nullable = false)
    private Levels level;

    @Column(name = "score", columnDefinition = "INT DEFAULT 0")
    private int score;

    @Column(name = "completed", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean completed;

    @Column(name = "completed_at")
    private int completedAt;

}
