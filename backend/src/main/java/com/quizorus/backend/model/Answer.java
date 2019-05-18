package com.quizorus.backend.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "answers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends UserDatabaseDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Question question;

    @OneToOne
    private Choice choice;

    @OneToOne
    private Content content;

    @ManyToOne
    private User user;

}
