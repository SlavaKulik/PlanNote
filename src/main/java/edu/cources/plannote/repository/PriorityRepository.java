package edu.cources.plannote.repository;

import edu.cources.plannote.entity.PriorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityRepository extends JpaRepository<PriorityEntity, String> {

}
