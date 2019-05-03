package com.quizorus.backend.repository;

import com.quizorus.backend.model.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Long> {
    Optional<TopicEntity> findById(Long topicId);

    List<TopicEntity> findByCreatedBy(Long userId);

    long countByCreatedBy(Long userId);

    void deleteById(Long topicId);

}
