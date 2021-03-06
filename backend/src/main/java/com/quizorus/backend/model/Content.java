package com.quizorus.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "contents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content extends UserDatabaseDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String title;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String text;

    @JsonIgnore
    @ManyToOne
    private Topic topic;

    @Nullable
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
    private List<Question> questionList;

}
