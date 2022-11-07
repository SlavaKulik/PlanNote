package edu.cources.plannote.repository;

import edu.cources.plannote.dto.ProjectDto;
import edu.cources.plannote.entity.ProjectEntity;
import edu.cources.plannote.entity.UserEntity;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {

//    @Query(value = "SELECT project_id from user_project_list WHERE user_id = :idusers",
//                        nativeQuery = true)
//    List<UUID> getProjectsByUserId(@Param("idusers") UUID id);
    @Modifying
    @Query(value = "INSERT INTO user_project_list VALUES((SELECT user_list.id FROM user_list WHERE user_name = :userName), :projectId)" , nativeQuery = true)
    @Transactional
    void addUserToProject(@Param("userName") String userName, @Param("projectId") UUID projectId);

    @Query(value = "select project.projectName from ProjectEntity project where project.projectId = :projectId")
    String getProjectNameById(@Param("projectId") UUID projectId);
//    @Modifying
//    @Transactional
//    @Query(value = "update ProjectEntity project set project.users = :newUser where project.projectId = :projectId")
//    void addUserToProject(@Param("newUser") Set<UserEntity> newUser, @Param("projectId") UUID projectId);
}