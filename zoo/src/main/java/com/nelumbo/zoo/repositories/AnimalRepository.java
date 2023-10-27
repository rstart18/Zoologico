package com.nelumbo.zoo.repositories;

import com.nelumbo.zoo.entities.AnimalEntity;
import com.nelumbo.zoo.entities.SpeciesEntity;
import org.springframework.data.jpa.repository.Query;
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

    @Query("SELECT a FROM AnimalEntity a WHERE lower(a.name) LIKE lower('%' || :query || '%')")
    List<AnimalEntity> findByNameContaining(String query);

}
