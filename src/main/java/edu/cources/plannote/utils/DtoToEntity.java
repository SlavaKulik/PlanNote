package edu.cources.plannote.utils;

import edu.cources.plannote.dto.ProjectDto;
import edu.cources.plannote.dto.SubtaskDto;
import edu.cources.plannote.dto.TaskDto;
import edu.cources.plannote.dto.UserDto;
import edu.cources.plannote.entity.ProjectEntity;
import edu.cources.plannote.entity.SubtaskEntity;
import edu.cources.plannote.entity.TaskEntity;
import edu.cources.plannote.entity.UserEntity;
import java.time.Instant;

public class DtoToEntity {

    public static ProjectEntity projectDtoToEntity(ProjectDto projectData) {
        return ProjectEntity.builder()
                .projectName(projectData.getProjectName())
                .users(projectData.getUsers())
                .build();
    }

    public static TaskEntity taskDtoToEntity(TaskDto taskData) {
        return TaskEntity.builder()
                .taskName(taskData.getTaskName())
                .projectTask(taskData.getProjectTask())
                .userTask(taskData.getUserTask())
                .taskStatus(taskData.getStatusTask())
                .taskTimeStart(Instant.parse(taskData.getStartTime()))
                .taskTimeEnd(Instant.parse(taskData.getEndTime()))
                .taskLabel(taskData.getLabelTask())
                .taskPriority(taskData.getPriorityTask())
                .transactions(taskData.getTransactions())
                .subtasks(taskData.getSubtasks())
                .build();
    }

    public static SubtaskEntity subtaskDtoToEntity(SubtaskDto subtaskData) {
        return SubtaskEntity.builder()
                .subtaskName(subtaskData.getSubtaskName())
                .taskSubtask(subtaskData.getTask())
                .subtaskTimeStart(Instant.parse(subtaskData.getStartTime()))
                .subtaskTimeEnd(Instant.parse(subtaskData.getEndTime()))
                .build();
    }

    public static UserEntity userDtoToEntity(UserDto userData) {
        return UserEntity.builder()
                .userName(userData.getUserName())
                .userPassword(userData.getUserPassword())
                .userPosition(userData.getUserPosition())
                .accountStatus(userData.getAccountStatus())
                .userScore(userData.getScore())
                .userStatus(userData.getUserStatus())
                .userRole(userData.getUserRole())
                .tasks(userData.getTasks())
                .build();
    }
}
