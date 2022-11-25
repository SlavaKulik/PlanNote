package edu.courses.plannote.repository;

import edu.courses.plannote.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByUserName(String name);

    List<UserEntity> findUserEntitiesByIdentifier(UUID id);
}
