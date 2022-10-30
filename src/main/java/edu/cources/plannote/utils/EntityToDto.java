package edu.cources.plannote.utils;

import edu.cources.plannote.dto.ProjectDto;
import edu.cources.plannote.dto.SubtaskDto;
import edu.cources.plannote.dto.TaskDto;
import edu.cources.plannote.dto.UserDto;
import edu.cources.plannote.entity.ProjectEntity;
import edu.cources.plannote.entity.SubtaskEntity;
import edu.cources.plannote.entity.TaskEntity;
import edu.cources.plannote.entity.UserEntity;

import java.util.UUID;

public class EntityToDto {
    public static ProjectDto productEntityToDto(ProjectEntity project) {
        return ProjectDto.builder()
                .id(String.valueOf(project.getProjectId()))
                .projectName(project.getProjectName())
                .users(project.getUsers())
                .tasks(project.getTasks())
                .build();
    }
    public static SubtaskDto subtaskEntityToDto(SubtaskEntity subtask) {
        return SubtaskDto.builder()
                .id(String.valueOf(subtask.getSubtaskId()))
                .subtaskName(subtask.getSubtaskName())
                .task(subtask.getTaskSubtask())
                .startTime(String.valueOf(subtask.getSubtaskTimeStart()))
                .endTime(String.valueOf(subtask.getSubtaskTimeEnd()))
                .build();
    }
    public static TaskDto taskEntityToDto(TaskEntity task) {
        return TaskDto.builder()
                .id(String.valueOf(task.getTaskId()))
                .taskName(task.getTaskName())
                .projectTask(task.getProjectTask())
                .userTask(task.getUserTask())
                .statusTask(task.getTaskStatus())
                .startTime(String.valueOf(task.getTaskTimeStart()))
                .endTime(String.valueOf(task.getTaskTimeEnd()))
                .labelTask(task.getTaskLabel())
                .priorityTask(task.getTaskPriority())
                .transactions(task.getTransactions())
                .subtasks(task.getSubtasks())
                .build();
    }
    public static UserDto userEntityToDto(UserEntity user) {
        return UserDto.builder()
                .id(String.valueOf(user.getIdentifier()))
                .userName(user.getUsername())
                .userPassword(user.getUserPassword())
                .userPosition(user.getUserPosition())
                .accountStatus(user.getAccountStatus())
                .userScore(user.getUserScore())
                .userStatus(user.getUserStatus())
                .projects(user.getProjects())
                .tasks(user.getTasks())
                .build();
    }
 }
