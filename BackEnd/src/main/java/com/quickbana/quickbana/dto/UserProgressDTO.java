package com.quickbana.quickbana.dto;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserProgressDTO {

    private Long id;
    private Long userId;
    private Long levelId;
    private int score;
    private boolean completed;
    private int completedAt;

}
