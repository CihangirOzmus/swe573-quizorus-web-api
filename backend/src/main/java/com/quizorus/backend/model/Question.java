package com.quizorus.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question extends UserDatabaseDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String text;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Choice> choiceList;

    @JsonIgnore
    @ManyToOne
    private Content content;

//    @OneToMany
//    @JoinTable(name = "answers",
//            joinColumns = @JoinColumn(name = "choice_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    @MapKeyJoinColumn(name = "question_id")
//    private Map<Question, User> answers = new HashMap<>();

}
