package com.quizorus.backend.repository;

import com.quizorus.backend.model.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<ContentEntity, Long> {

    Optional<ContentEntity> findById(Long contentId);
    void deleteContentById(Long contentId);

}
