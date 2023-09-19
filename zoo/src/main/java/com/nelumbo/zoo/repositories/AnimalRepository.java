package com.nelumbo.zoo.repositories;

import com.nelumbo.zoo.entities.AnimalEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends CrudRepository<AnimalEntity, Long> {
    Long countBySpeciesId(Long speciesId);
    boolean existsByZoneId(Long zoneId);
    Long countByZoneId(Long zoneId);
    boolean existsBySpeciesId(Long speciesId);
}
