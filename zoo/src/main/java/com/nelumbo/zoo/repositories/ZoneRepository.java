package com.nelumbo.zoo.repositories;

import com.nelumbo.zoo.entities.ZoneEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends CrudRepository<ZoneEntity, Long> {
    @Query("SELECT z FROM ZoneEntity z WHERE lower(z.name) LIKE lower('%' || :query || '%')")
    List<ZoneEntity> findByNameContaining(String query);
}
