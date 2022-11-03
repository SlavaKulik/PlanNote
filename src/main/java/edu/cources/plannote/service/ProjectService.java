package edu.cources.plannote.service;

import edu.cources.plannote.dto.ProjectDto;
import edu.cources.plannote.dto.TaskDto;
import edu.cources.plannote.entity.ProjectEntity;
import edu.cources.plannote.entity.SubtaskEntity;
import edu.cources.plannote.entity.TaskEntity;
import edu.cources.plannote.entity.UserEntity;

import java.time.Instant;
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

    void addNewTask(TaskEntity task);

    void deleteTask(TaskEntity task);

    void changeTask(UUID id, String newName);

    List<ProjectEntity> projectList();

    void createNewProject(ProjectDto project);

//    List<UUID> getProjectsByUserId(UUID id);
}
