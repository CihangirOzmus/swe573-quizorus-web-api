package com.quizorus.backend.model;

import javax.persistence.*;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private Question question;

    @OneToOne
    private Content content;
}
