package edu.cources.plannote.repository;

import edu.cources.plannote.entity.LabelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LabelRepository extends JpaRepository<LabelEntity, UUID> {

}
