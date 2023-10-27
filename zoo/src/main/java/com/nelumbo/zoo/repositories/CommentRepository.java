package com.nelumbo.zoo.repositories;

import com.nelumbo.zoo.entities.AnimalEntity;
import com.nelumbo.zoo.entities.CommentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Long> {
    List<CommentEntity> findByRepliesNotNull();
    List<CommentEntity> findByAnimalId(Long animalId);
    @Query("SELECT c FROM CommentEntity c WHERE lower(c.body) LIKE lower('%' || :query || '%')")
    List<CommentEntity> findByBodyContaining(String query);
}
