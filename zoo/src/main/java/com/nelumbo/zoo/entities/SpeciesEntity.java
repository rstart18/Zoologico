package com.nelumbo.zoo.entities;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "species")
public class SpeciesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    @Column(unique = true)
    private String name;

    @Column(name = "zone_id")
    private Long zoneId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "species_id", referencedColumnName = "id" )
    private List<AnimalEntity> animals;
}