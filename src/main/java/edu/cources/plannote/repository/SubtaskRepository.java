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
    @Query(value = "select subtask from SubtaskEntity subtask where subtask.taskSubtask.taskId = :taskId")
    List<SubtaskEntity> findSubtasksByTaskId(@Param("taskId") UUID taskId);
    @Modifying
    @Query(value = "update SubtaskEntity subtask set subtask.subtaskName = :newName where subtask.subtaskId = :id")
    @Transactional
    void changeSubtaskName(@Param(value = "id") UUID id, @Param(value = "newName") String newName);

    @Modifying
    @Query(value = "update SubtaskEntity subtask set subtask.subtaskTimeStart = :newTime where subtask.subtaskId = :id")
    @Transactional
    void changeSubtaskStartTime(@Param(value = "id") UUID id, @Param(value = "newTime") LocalDateTime newTime);

    @Modifying
    @Query(value = "update SubtaskEntity subtask set subtask.subtaskTimeEnd = :newTime where subtask.subtaskId = :id")
    @Transactional
    void changeSubtaskEndTime(@Param(value = "id") UUID id, @Param(value = "newTime") LocalDateTime newTime);
}
