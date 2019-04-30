package com.quizorus.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quizorus.backend.model.audit.UserDateAudit;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "contents")
public class Content extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String title;

    @NotBlank
    @Size(max = 1000)
    private String text;

    @JsonIgnore
    @ManyToOne
    private Topic topic;

    @Nullable
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
    private List<Question> questionList;

    @Nullable
    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(@Nullable List<Question> questionList) {
        this.questionList = questionList;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
