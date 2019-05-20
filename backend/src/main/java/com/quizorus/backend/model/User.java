package com.quizorus.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends DatabaseDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NaturalId
    @NotBlank
    @Size(max = 15)
    @Column(unique = true)
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;

    @JsonIgnore
    @Nullable
    @ManyToMany(mappedBy = "enrolledUsers")
    private List<Topic> enrolledTopics;

}
