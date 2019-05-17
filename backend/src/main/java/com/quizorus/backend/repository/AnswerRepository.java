package com.quizorus.backend.repository;

import com.quizorus.backend.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Answer findByQuestionIdAndUserId(Long questionId, Long userId);
    Optional<Answer> findByQuestionId(Long questionId);

}
