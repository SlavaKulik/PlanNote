package edu.cources.plannote.repository;

import edu.cources.plannote.entity.SubtaskEntity;
import edu.cources.plannote.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface SubtaskRepository extends JpaRepository<SubtaskEntity, UUID>{

    List<SubtaskEntity> findSubtaskEntityByTaskSubtask(TaskEntity taskSubtask);
}
