package com.quizorus.backend.model;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topics")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Topic extends UserDatabaseDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String title;

    @NotBlank
    @Size(max = 255)
    private String description;

    @Nullable
    private String imageUrl;

    @Nullable
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic")
    private List<Content> contentList;

    @Nullable
    @ManyToMany
    @JoinTable(name = "enrolled_topic_list",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private List<User> enrolledUserList;

    @Nullable
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "topic_wikiData",
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns = @JoinColumn(name = "wikiData_id"))
    private List<WikiData> wikiData;

}
