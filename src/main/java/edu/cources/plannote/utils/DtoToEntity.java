package edu.cources.plannote.utils;

import edu.cources.plannote.dto.*;
import edu.cources.plannote.entity.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
                .projectTask(taskData.getProject())
                .userTask(taskData.getUser())
                .taskStatus(taskData.getStatus())
                .taskTimeStart(LocalDateTime.parse(taskData.getStartTime()))
                .taskTimeEnd(LocalDateTime.parse(taskData.getEndTime()))
                .taskPriority(taskData.getPriority())
                .subtasks(taskData.getSubtasks())
                .build();
    }

    public static SubtaskEntity subtaskDtoToEntity(SubtaskDto subtaskData) {
        return SubtaskEntity.builder()
                .subtaskName(subtaskData.getSubtaskName())
                .taskSubtask(subtaskData.getTask())
                .subtaskTimeStart(LocalDateTime.parse(subtaskData.getStartTime()))
                .subtaskTimeEnd(LocalDateTime.parse(subtaskData.getEndTime()))
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

    public static TransactionEntity transactionDtoToEntity(TransactionDto transactionData) {
        return TransactionEntity.builder()
                .transactionName(transactionData.getTransactionName())
                .transactionMoneyFlow(BigDecimal.valueOf(Double.parseDouble(transactionData.getTransactionMoneyFlow())))
                .taskTransaction(transactionData.getTask())
                .build();
    }
}
