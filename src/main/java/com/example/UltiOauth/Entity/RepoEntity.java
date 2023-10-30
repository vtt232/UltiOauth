package com.example.UltiOauth.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder(toBuilder = true)
@Table(name = "repo_table")
public class RepoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String repo_name;
    private String repo_url;
    private String language;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @OneToMany(mappedBy = "repo", cascade= CascadeType.ALL)
    private List<NoteEntity> noteEntity;


}