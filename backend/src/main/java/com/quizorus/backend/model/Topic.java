package com.quizorus.backend.model;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @Column(columnDefinition = "TEXT", unique = true)
    private String title;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String description;

    @Nullable
    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Nullable
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic")
    private List<Content> contentList;

    @Nullable
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "topic_wikidata",
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns = @JoinColumn(name = "wikidata_id"))
    private List<WikiData> wikiDataList;

    @Nullable
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "enrolled_users",
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> enrolledUsers;


}
