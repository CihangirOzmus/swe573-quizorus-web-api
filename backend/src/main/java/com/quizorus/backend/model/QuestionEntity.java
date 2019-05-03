package com.quizorus.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quizorus.backend.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "questions")
public class QuestionEntity extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String text;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<ChoiceEntity> choiceList;

    @JsonIgnore
    @ManyToOne
    private ContentEntity content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ChoiceEntity> getChoiceList() {
        return choiceList;
    }

    public void setChoiceList(List<ChoiceEntity> choiceList) {
        this.choiceList = choiceList;
    }

    public ContentEntity getContent() {
        return content;
    }

    public void setContent(ContentEntity content) {
        this.content = content;
    }
}
