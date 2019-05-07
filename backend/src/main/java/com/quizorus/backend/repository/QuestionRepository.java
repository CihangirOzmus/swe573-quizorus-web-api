package com.quizorus.backend.repository;

import com.quizorus.backend.model.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    Optional<QuestionEntity> findById(Long questionId);
    void deleteQuestionById(Long questionId);

}
