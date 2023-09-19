package com.nelumbo.zoo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    private String body;
    private String author;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "animal_id")
    private Long animalId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id", referencedColumnName = "id" )
    private List<ReplieEntity> replies;

    @PrePersist
    protected void onCreate() {
        date = new Date();
    }
}