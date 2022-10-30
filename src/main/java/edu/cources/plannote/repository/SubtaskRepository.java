package edu.cources.plannote.repository;

import edu.cources.plannote.entity.SubtaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface SubtaskRepository extends JpaRepository<SubtaskEntity, UUID> {
    @Modifying
    @Query(value = "update SubtaskEntity subtask set subtask.subtaskName = :newName where subtask.subtaskId = :id")
    void changeSubtaskName(@Param(value = "id") UUID id, @Param(value = "newName") String newName);

    @Modifying
    @Query(value = "update SubtaskEntity subtask set subtask.subtaskTimeStart = :newTime where subtask.subtaskId = :id")
    void changeSubtaskStartTime(@Param(value = "id") UUID id, @Param(value = "newTime") Instant newTime);

    @Modifying
    @Query(value = "update SubtaskEntity subtask set subtask.subtaskTimeEnd = :newTime where subtask.subtaskId = :id")
    void changeSubtaskEndTime(@Param(value = "id") UUID id, @Param(value = "newTime") Instant newTime);
}
