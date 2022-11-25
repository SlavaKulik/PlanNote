package edu.courses.plannote.repository;

import edu.courses.plannote.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {
    ProjectEntity findProjectByProjectId(UUID projectId);

    @Modifying
    @Transactional
    @Query(value = "delete from user_project_list where project_id = :projectId and user_id = :userId", nativeQuery = true)
    void deleteUserFromProject(@Param("projectId") UUID projectId, @Param("userId") UUID userId);

    @Modifying
    @Transactional
    void deleteProjectEntityByProjectId(UUID id);
}