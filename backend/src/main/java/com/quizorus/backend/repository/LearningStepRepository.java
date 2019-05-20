package com.quizorus.backend.repository;

import com.quizorus.backend.model.LearningStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LearningStepRepository extends JpaRepository<LearningStep, Long> {

    Optional<LearningStep> findByUserIdAndContentIdAndQuestionIdAndAnswerId(Long userId, Long contentId, Long questionId, Long answerId);
    List<LearningStep> findByUserIdAndContentId(Long userId, Long contentId);
    List<LearningStep> findByUserId(Long userId);
}
