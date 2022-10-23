package edu.cources.plannote.repository;

import edu.cources.plannote.entity.SubtaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubtaskRepository extends JpaRepository<SubtaskEntity, UUID> {

}
