package edu.cources.plannote.repository;

import edu.cources.plannote.entity.PriorityEntity;
import edu.cources.plannote.entity.StatusEntity;
import edu.cources.plannote.entity.TaskEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
    @Query(value = "select tasks from TaskEntity tasks where tasks.projectTask.projectId = :projectId")
    List<TaskEntity> findTasksByProjectId(@Param("projectId") UUID projectId);

    @Query(value = "select tasks from TaskEntity tasks where tasks.projectTask.projectId = :projectId and tasks.userTask.identifier = :userId order by tasks.taskStatus.statusId, tasks.taskPriority.priorityId")
    List<TaskEntity> findTasksByProjectIdAndUserId(@Param("projectId") UUID projectId, @Param("userId") UUID userId);
}
