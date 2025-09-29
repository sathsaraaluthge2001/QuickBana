package com.quickbana.quickbana.service;

import com.quickbana.quickbana.dto.CompleteLevelDTO;
import com.quickbana.quickbana.dto.ScoreResponseDTO;
import com.quickbana.quickbana.dto.UserDTO;
import com.quickbana.quickbana.dto.UserProgressDTO;
import com.quickbana.quickbana.entity.Levels;
import com.quickbana.quickbana.entity.User;
import com.quickbana.quickbana.entity.UserProgress;
import com.quickbana.quickbana.exception.UserNotFoundException;
import com.quickbana.quickbana.repository.LevelRepositry;
import com.quickbana.quickbana.repository.UserProgressRepository;
import com.quickbana.quickbana.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserProgressServiceImpl implements UserProgressService {

    private static final Logger logger = LoggerFactory.getLogger(UserProgressServiceImpl.class);

    private final UserProgressRepository userProgressRepository;
    private final UserRepository userRepository;
    private final LevelRepositry levelRepository;

    public UserProgressServiceImpl(UserProgressRepository userProgressRepository, UserRepository userRepository, LevelRepositry levelRepository) {
        this.userProgressRepository = userProgressRepository;
        this.userRepository = userRepository;
        this.levelRepository = levelRepository;
    }

    private UserProgressDTO mapToDTO(UserProgress userProgress) {
        logger.info("Mapping UserProgress entity to DTO - ID: {}", userProgress.getId());

        UserProgressDTO dto = new UserProgressDTO();
        dto.setId(userProgress.getId().longValue());
        dto.setUserId(userProgress.getUser().getId().longValue());
        dto.setLevelId(userProgress.getLevel().getId().longValue());
        dto.setScore(userProgress.getScore());
        dto.setCompleted(userProgress.isCompleted());
        dto.setCompletedAt(userProgress.getCompletedAt());

        logger.info("Mapped DTO - ID: {}, UserId: {}, LevelId: {}, CorrectAnswers: {}, Completed: {}",
                dto.getId(), dto.getUserId(), dto.getLevelId(), dto.getScore(), dto.isCompleted());

        return dto;
    }

    @Override
    public UserProgressDTO getUserProgress(Long userId) {
        logger.warn("getUserProgress method is not implemented yet.");
        return null;
    }

    @Override
    public UserProgressDTO updateUserProgress(Long userId, Long levelId, boolean isCorrect) {
        logger.warn("updateUserProgress method is not implemented yet.");
        return null;
    }

    @Override
    public List<UserProgressDTO> getLeaderboard() {

        logger.warn("getLeaderboard method is not implemented yet.");
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public UserProgressDTO insertProgress(Long userId, Long levelId, int time, int correctAnswer, boolean completed) {

        logger.info("Processing user progress for UserId: {}, LevelId: {}", userId, levelId);

        // Check user state (insert, update, no action)
        String userState = checkUserState(userId, levelId, time);
        logger.info("User state determined: {}", userState);

        if ("noaction".equals(userState)) {
            logger.info("No action required for UserId: {}, LevelId: {}", userId, levelId);
            return null;
        }

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> {
                        logger.error("User not found with ID: {}", userId);
                        return new UserNotFoundException("User not found");
                    });

            Levels level = levelRepository.findById(levelId)
                    .orElseThrow(() -> {
                        logger.error("Level not found with ID: {}", levelId);
                        return new RuntimeException("Level not found");
                    });

            UserProgress progress;

            if ("update".equals(userState)) {
                progress = userProgressRepository.findByUserIdAndLevelId(userId, levelId)
                        .orElseThrow(() -> new RuntimeException("Existing progress not found"));
                progress.setScore(correctAnswer);
                progress.setCompleted(completed);
                progress.setCompletedAt(time);
                logger.info("Updating existing progress for UserId: {}, LevelId: {}", userId, levelId);
            } else {
                progress = new UserProgress();
                progress.setUser(user);
                progress.setLevel(level);
                progress.setScore(correctAnswer);
                progress.setCompleted(completed);
                progress.setCompletedAt(time);
                logger.info("Inserting new progress for UserId: {}, LevelId: {}", userId, levelId);
            }

            userProgressRepository.save(progress);
            return mapToDTO(progress);

        } catch (Exception e) {
            logger.error("Error while processing progress for UserId: {}, LevelId: {} - Error: {}",
                    userId, levelId, e.getMessage(), e);
            throw e;
        }
    }

    private String checkUserState(Long userId, Long levelId, int time) {

        Optional<UserProgress> userProgressOpt = userProgressRepository.findByUserIdAndLevelId(userId, levelId);

        if (!userProgressOpt.isPresent()) {
            return "insert";
        }

        UserProgress userProgress = userProgressOpt.get();

        if (userProgress.getCompletedAt() < time) {
            return "update";
        } else {
            return "noaction";
        }
    }

    @Override
    public List<ScoreResponseDTO> getScores() {
        logger.info("Fetching scores from user progress...");

        List<UserProgress> userProgressList = userProgressRepository.findAllSortedByScoreAndTime();
        Map<Long, Integer> userScoreMap = new HashMap<>();
        Map<Long, String> userNameMap = new HashMap<>();

        if (userProgressList.isEmpty()) {
            logger.warn("No user progress records found.");
        }

        for (UserProgress userPro : userProgressList) {
            try {
                Long userId = userPro.getUser().getId();
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

                String name = user.getName();
                int scoreTot = userPro.getScore() + userPro.getCompletedAt();

                // Aggregate scores
                userScoreMap.put(userId, userScoreMap.getOrDefault(userId, 0) + scoreTot);
                userNameMap.putIfAbsent(userId, name);

            } catch (Exception e) {
                logger.error("Error processing UserProgress ID: {} - Error: {}", userPro.getId(), e.getMessage(), e);
            }
        }

        // Convert to List and Sort by Score (Descending Order)
        List<ScoreResponseDTO> responseList = userScoreMap.entrySet()
                .stream()
                .map(entry -> {
                    ScoreResponseDTO dto = new ScoreResponseDTO();
                    dto.setScore(entry.getValue());
                    dto.setName(userNameMap.get(entry.getKey()));
                    return dto;
                })
                .sorted(Comparator.comparingInt(ScoreResponseDTO::getScore).reversed()) // Sort by Score DESC
                .collect(Collectors.toList());

        logger.info("Finished processing user scores. Total users processed: {}", responseList.size());
        return responseList;
    }


    @Override
    public List<CompleteLevelDTO> getComLev(Long userId) {
        List<Integer> completedLevels = userProgressRepository.findAllCompletedLevel(userId);
        return completedLevels.stream().map(level -> {
            CompleteLevelDTO dto = new CompleteLevelDTO();
            dto.setLevelNumbers(level);
            return dto;
        }).collect(Collectors.toList());
    }

}
