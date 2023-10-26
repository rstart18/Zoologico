package com.nelumbo.zoo.entities;

import com.nelumbo.zoo.repositories.SpeciesRepository;
import com.nelumbo.zoo.repositories.ZoneRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @Column(name = "species_id")
    private Long speciesId;

    @Column(name = "zone_id")
    private Long zoneId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "animal_id", referencedColumnName = "id" )
    private List<CommentEntity> comments;

}