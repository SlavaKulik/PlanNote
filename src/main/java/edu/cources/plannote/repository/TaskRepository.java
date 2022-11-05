package edu.cources.plannote.repository;

import edu.cources.plannote.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
    @Modifying
    @Query(value = "update TaskEntity task set task.taskName = :newName where task.taskId = :id")
    void changeTask(@Param(value = "id") UUID id, @Param(value = "newName") String newName);

    @Query(value = "select tasks from TaskEntity tasks where tasks.projectTask.projectId = :projectId")
    List<TaskEntity> findTasksByProjectId(@Param("projectId") UUID projectId);
}
