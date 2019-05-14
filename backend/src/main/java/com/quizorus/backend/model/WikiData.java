package com.quizorus.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "wikidata")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WikiData extends UserDatabaseDateAudit{

    @Id
    @NotBlank
    private String id;

    @NotBlank
    private String label;

    @NotBlank
    private String description;

    @NotBlank
    private String conceptUri;

}
