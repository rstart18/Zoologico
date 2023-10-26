package com.nelumbo.zoo.repositories;

import com.nelumbo.zoo.entities.AnimalEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface AnimalRepository extends CrudRepository<AnimalEntity, Long> {
    Long countBySpeciesId(Long speciesId);
    boolean existsByZoneId(Long zoneId);
    Long countByZoneId(Long zoneId);
    boolean existsBySpeciesId(Long speciesId);
    List<AnimalEntity> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
