package com.nelumbo.zoo.repositories;

import com.nelumbo.zoo.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findOneByEmail(String email);

    long countByRole(String role);

    Optional<UserEntity> findById(Long id);

}
