package com.quickbana.quickbana.controller;

import com.quickbana.quickbana.dto.CompleteLevelDTO;
import com.quickbana.quickbana.dto.ScoreResponseDTO;
import com.quickbana.quickbana.dto.UserProgressDTO;
import com.quickbana.quickbana.service.UserProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-progress")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserProgressController {

    private final UserProgressService userProgressService;

    public UserProgressController(UserProgressService userProgressService) {
        this.userProgressService = userProgressService;
    }

    @GetMapping("/{userId}/{levelId}")
    public UserProgressDTO getUserProgress(@PathVariable Long userId) {
        return userProgressService.getUserProgress(userId);
    }

    @PostMapping("/{userId}/{levelId}")
    public UserProgressDTO updateUserProgress(@PathVariable Long userId, @PathVariable Long levelId, @RequestParam boolean isCorrect) {
        return userProgressService.updateUserProgress(userId, levelId, isCorrect);
    }

    @GetMapping("/leaderboard")
    public List<UserProgressDTO> getLeaderboard() {
        return userProgressService.getLeaderboard();
    }

    @GetMapping("/score-board")
    public List<ScoreResponseDTO> getScore(){
        return userProgressService.getScores();
    }

    @GetMapping("/complete-level/{userId}")
    public List<CompleteLevelDTO> getCompleteLevel(@PathVariable Long userId){
        return userProgressService.getComLev(userId);
    }



}
