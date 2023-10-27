package com.nelumbo.zoo.repositories;

import com.nelumbo.zoo.entities.SpeciesEntity;
import com.nelumbo.zoo.entities.ZoneEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpeciesRepository extends CrudRepository<SpeciesEntity, Long> {
    boolean existsByName(String name);
    @Query("SELECT s FROM SpeciesEntity s WHERE lower(s.name) LIKE lower('%' || :query || '%')")
    List<SpeciesEntity> findByNameContaining(String query);

}
