package edu.courses.plannote.utils;

import edu.courses.plannote.dto.*;
import edu.courses.plannote.entity.*;
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
                .taskTimeEnd(parseTime(taskData.getEndTime()))
                .taskPriority(taskData.getPriority())
                .subtasks(taskData.getSubtasks())
                .build();
    }

    public static SubtaskEntity subtaskDtoToEntity(SubtaskDto subtaskData) {
        return SubtaskEntity.builder()
                .subtaskName(subtaskData.getSubtaskName())
                .taskSubtask(subtaskData.getTask())
                .subtaskTimeEnd(parseTime(subtaskData.getEndTime()))
                .build();
    }

    public static UserEntity userDtoToEntity(UserDto userData) {
        return UserEntity.builder()
                .userName(userData.getUserName())
                .userPassword(userData.getUserPassword())
                .userPosition(userData.getUserPosition())
                .accountStatus(userData.getAccountStatus())
                .userScore(userData.getScore())
                .userRole(userData.getUserRole())
                .tasks(userData.getTasks())
                .build();
    }

    public static TransactionEntity transactionDtoToEntity(TransactionDto transactionData) {
        return TransactionEntity.builder()
                .transactionName(transactionData.getTransactionName())
                .transactionMoneyFlow(valueOfTransaction(transactionData))
                .taskTransaction(transactionData.getTask())
                .build();
    }

    private static BigDecimal valueOfTransaction(TransactionDto transaction) {
        return BigDecimal.valueOf(Double.parseDouble(transaction.getTransactionMoneyFlow()));
    }

    private static LocalDateTime parseTime(String time) {
        return LocalDateTime.parse(time);
    }
}
