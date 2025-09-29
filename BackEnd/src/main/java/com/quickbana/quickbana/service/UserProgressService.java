package com.quickbana.quickbana.service;

import com.quickbana.quickbana.dto.ScoreResponseDTO;
import com.quickbana.quickbana.dto.UserProgressDTO;
import com.quickbana.quickbana.dto.CompleteLevelDTO;

import java.util.List;

public interface UserProgressService {
    UserProgressDTO getUserProgress(Long userId);
    UserProgressDTO updateUserProgress(Long userId, Long levelId, boolean isCorrect);
    List<UserProgressDTO> getLeaderboard();
    UserProgressDTO insertProgress(Long userId, Long levelId,int time,int score,boolean completed);
    List<ScoreResponseDTO> getScores();

    List<CompleteLevelDTO> getComLev(Long userId);

}

