package edu.courses.plannote.repository;

import edu.courses.plannote.entity.SubtaskEntity;
import edu.courses.plannote.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubtaskRepository extends JpaRepository<SubtaskEntity, UUID>{
    List<SubtaskEntity> findSubtaskEntityByTaskSubtask(TaskEntity taskSubtask);
}
