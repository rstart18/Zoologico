package com.nelumbo.zoo.repositories;

import com.nelumbo.zoo.entities.CommentEntity;
import com.nelumbo.zoo.entities.ReplieEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplieRepository extends CrudRepository<ReplieEntity, Long> {
    @Query("SELECT r FROM ReplieEntity r WHERE lower(r.body) LIKE lower('%' || :query || '%')")
    List<ReplieEntity> findByBodyContaining(String query);
}
