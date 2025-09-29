package com.quickbana.quickbana.repository;

import com.quickbana.quickbana.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {

    Optional<UserProgress> findByUserIdAndLevelId(Long userId, Long levelId);
    Optional<UserProgress> findByUserId(Long userId);

    @Query("SELECT u FROM UserProgress u ORDER BY (u.score + u.completedAt) DESC")
    List<UserProgress> findAllSortedByScoreAndTime();

    @Query(value = "SELECT level_id FROM user_progress WHERE user_id = ?1 AND completed = true", nativeQuery = true)
    List<Integer> findAllCompletedLevel(Long userId);

    @Query(value = "SELECT * FROM user_progress WHERE user_id = ?1 AND level_id = ?2", nativeQuery = true)
    Optional<UserProgress> findByUIDandLID(Long userId, Long levelId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_progress SET time = ?1 WHERE user_id = ?2 AND level_id = ?3", nativeQuery = true)
    int updateProgress(int time, Long userId, Long levelId);
}
