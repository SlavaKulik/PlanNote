package edu.cources.plannote.service;

import edu.cources.plannote.dto.ProjectDto;
import edu.cources.plannote.dto.TaskDto;
import edu.cources.plannote.entity.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProjectService {

    List<SubtaskEntity> subtaskList();

    void addNewSubtask(SubtaskEntity subtask);

    void deleteSubtask(SubtaskEntity subtask);

    void changeSubtaskName(UUID id, String newName);

    void changeSubtaskStartTime(UUID id, Instant newTime);

    void changeSubtaskEndTime(UUID id, Instant newTime);

    List<TaskDto> taskList();

    void addNewTask(TaskDto task);

    void changeTaskName(UUID id, String newName);

    void changeTaskStatus(UUID id, StatusEntity newStatus);

    void changeTaskTimeFrom(UUID id, LocalDateTime newTime);

    void changeTaskTimeEnd(UUID id, LocalDateTime newTime);

    void changeTaskPriority(UUID id, PriorityEntity newPriority);

    List<TaskDto> findTasksByProjectId(UUID projectId);

//    List<TaskDto> findTasksByProjectName(UUID userId, String projectName);

//    List<ProjectEntity> projectList();

    List<TaskDto> findTasksByProjectIdAndUserId(UUID projectId, UUID userId);

    void createNewProject(ProjectDto project);

    void addUserToProject(String userName, UUID projectId);

    String getProjectNameById(UUID projectId);

//    List<UUID> getProjectsByUserId(UUID id);
}
