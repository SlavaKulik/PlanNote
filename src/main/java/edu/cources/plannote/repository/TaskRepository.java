package edu.cources.plannote.repository;

import edu.cources.plannote.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID>, PagingAndSortingRepository<TaskEntity, UUID> {

    @Query(value = "select tasks from TaskEntity tasks where tasks.projectTask.projectId = :projectId and tasks.userTask.identifier = :userId order by tasks.taskStatus.statusId, case " +
            "when tasks.taskPriority.priorityId = 'High' then 1 " +
            "when tasks.taskPriority.priorityId = 'Mid' then 2 " +
            "when tasks.taskPriority.priorityId = 'Low' then 3 " +
            "end asc")
    List<TaskEntity> findTasksByProjectIdAndUserId(@Param("projectId") UUID projectId, @Param("userId") UUID userId);
}
