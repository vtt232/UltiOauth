package com.example.UltiOauth.Entity;


import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name = "note_table")
@Builder(toBuilder = true)
public class NoteEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "repo_id")
    private RepoEntity repo;


}
