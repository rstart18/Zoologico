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
@Table(name = "zones")
public class ZoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "zone_id", referencedColumnName = "id" )
    private List<SpeciesEntity> zones;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "zone_id", referencedColumnName = "id" )
    private List<AnimalEntity> animals;

}