package com.nelumbo.zoo.repositories;

import com.nelumbo.zoo.entities.ZoneEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends CrudRepository<ZoneEntity, Long> {
}
