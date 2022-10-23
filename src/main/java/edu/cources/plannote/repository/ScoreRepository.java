package edu.cources.plannote.repository;

import edu.cources.plannote.entity.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<ScoreEntity, String> {

}
