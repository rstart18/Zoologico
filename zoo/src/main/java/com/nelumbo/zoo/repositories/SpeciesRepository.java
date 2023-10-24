package com.nelumbo.zoo.repositories;

import com.nelumbo.zoo.entities.SpeciesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeciesRepository extends CrudRepository<SpeciesEntity, Long> {
    boolean existsByName(String name);
}
