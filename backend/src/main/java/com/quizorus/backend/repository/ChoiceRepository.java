package com.quizorus.backend.repository;

import com.quizorus.backend.model.ChoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<ChoiceEntity, Long> {
}
