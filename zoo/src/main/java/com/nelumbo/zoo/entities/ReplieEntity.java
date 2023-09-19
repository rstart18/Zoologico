package com.nelumbo.zoo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "replies")
public class ReplieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    private String body;
    private String author;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "comment_id")
    private Long commentId;

    @PrePersist
    protected void onCreate() {
        date = new Date();
    }
}