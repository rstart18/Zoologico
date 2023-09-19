package com.nelumbo.zoo.repositories;

import com.nelumbo.zoo.entities.ReplieEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplieRepository extends CrudRepository<ReplieEntity, Long> {
}
