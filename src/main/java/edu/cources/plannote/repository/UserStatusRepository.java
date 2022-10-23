package edu.cources.plannote.repository;

import edu.cources.plannote.entity.UserStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatusEntity, String> {
}
