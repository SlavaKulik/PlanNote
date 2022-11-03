package edu.cources.plannote.repository;

import edu.cources.plannote.entity.ProjectEntity;
import edu.cources.plannote.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {

//    @Query(value = "SELECT project_id from user_project_list WHERE user_id = :idusers",
//                        nativeQuery = true)
//    List<UUID> getProjectsByUserId(@Param("idusers") UUID id);
}