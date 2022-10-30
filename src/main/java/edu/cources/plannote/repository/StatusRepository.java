package edu.cources.plannote.repository;

import edu.cources.plannote.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, String> {
}
