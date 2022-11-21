package edu.cources.plannote.service;

import edu.cources.plannote.dto.ProjectDto;
import edu.cources.plannote.dto.SubtaskDto;
import edu.cources.plannote.dto.TaskDto;
import edu.cources.plannote.dto.UserDto;
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

    void addNewSubtask(SubtaskDto subtask);

    List<SubtaskDto> findSubtasksByTaskId(UUID taskId);

    void updateSubtaskFromDto(SubtaskDto subtaskDto);

    void addNewTask(TaskDto task);

    void updateTaskFromDto(TaskDto taskDto);

//    List<TaskDto> findTasksByProjectId(UUID projectId);

    List<TaskDto> findTasksByProjectIdAndUserId(UUID projectId, UUID userId);

    void createNewProject(ProjectDto project);

    void assignUserToProject(ProjectDto projectDto);

    String findProjectNameById(UUID projectId);

    List<UserDto> findUsersByProjectId(UUID projectId);

    void deleteUserFromProject(UUID projectId, UUID userId);
}
