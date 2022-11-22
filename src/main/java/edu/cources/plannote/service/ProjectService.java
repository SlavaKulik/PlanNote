package edu.cources.plannote.service;

import edu.cources.plannote.dto.ProjectDto;
import edu.cources.plannote.dto.SubtaskDto;
import edu.cources.plannote.dto.TaskDto;
import edu.cources.plannote.dto.UserDto;
import java.util.List;
import java.util.UUID;

public interface ProjectService {

    void addNewSubtask(SubtaskDto subtask);

    List<SubtaskDto> findSubtasksByTaskId(UUID taskId);

    void updateSubtaskFromDto(SubtaskDto subtaskDto);

    void addNewTask(TaskDto task);

    void updateTaskFromDto(TaskDto taskDto);

    List<TaskDto> findTasksByProjectIdAndUserId(UUID projectId, UUID userId);

    void createNewProject(ProjectDto project);

    void assignUserToProject(ProjectDto projectDto);

    String findProjectNameById(UUID projectId);

    List<UserDto> findUsersByProjectId(UUID projectId);

    void deleteUserFromProject(UUID projectId, UUID userId);

    void deleteProjectById(UUID projectId);
}
