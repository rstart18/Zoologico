package com.nelumbo.zoo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "animals")
public class AnimalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    private String name;

    @Column(name = "species_id")
    private Long speciesId;

    @Column(name = "zone_id")
    private Long zoneId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "animal_id", referencedColumnName = "id" )
    private List<CommentEntity> comments;

}