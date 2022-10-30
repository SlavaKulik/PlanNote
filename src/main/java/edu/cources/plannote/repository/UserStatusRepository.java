package edu.cources.plannote.repository;

import edu.cources.plannote.entity.UserStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatusEntity, String> {
}
