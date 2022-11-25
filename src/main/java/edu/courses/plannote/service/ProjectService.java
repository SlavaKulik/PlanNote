package edu.courses.plannote.service;

import edu.courses.plannote.dto.ProjectDto;
import edu.courses.plannote.dto.SubtaskDto;
import edu.courses.plannote.dto.TaskDto;
import edu.courses.plannote.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    void addNewSubtask(SubtaskDto subtask);

    List<SubtaskDto> findSubtasksByTaskId(UUID taskId);

    void updateSubtaskFromDto(SubtaskDto subtaskDto);

    void addNewTask(TaskDto task);

    void updateTaskFromDto(TaskDto taskDto);

    List<TaskDto> findTasksByProjectIdAndUserId(UUID projectId, UUID userId);

    List<TaskDto> findTaskByTaskId(UUID id);

    void createNewProject(ProjectDto project);

    void assignUserToProject(ProjectDto projectDto);

    String findProjectNameById(UUID projectId);

    List<UserDto> findUsersByProjectId(UUID projectId);

    void deleteUserFromProject(UUID projectId, UUID userId);

    void deleteProjectById(UUID projectId);
}
