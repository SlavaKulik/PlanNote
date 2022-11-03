package edu.cources.plannote.repository;

import edu.cources.plannote.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByUserName(String name);

    @Query(value = "select user from UserEntity user where user.userName = :name")
    List<UserEntity> getUsersByName(@Param("name") String name);

    @Query(value = "select user from UserEntity user where user.identifier = :id")
    List<UserEntity> getProjectsByUserId(@Param("id") UUID userId);
}
