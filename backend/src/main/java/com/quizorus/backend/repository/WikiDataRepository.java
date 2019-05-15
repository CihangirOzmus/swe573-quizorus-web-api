package com.quizorus.backend.repository;

import com.quizorus.backend.model.WikiData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WikiDataRepository extends JpaRepository<WikiData, String> {

}
