package edu.courses.plannote.dto;

import edu.courses.plannote.entity.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
public class TaskDto {
    private String id;

    private String taskName;

    private String projectTask;

    private ProjectEntity project;

    private String userTask;

    private UserEntity user;

    private String statusTask;

    private StatusEntity status;

    private String endTime;

    private String priorityTask;

    private PriorityEntity priority;

    private List<TransactionEntity> transactions;

    private List<String> transactionsName;

    private List<String> transactionsMoney;

    private Set<SubtaskEntity> subtasks;

    private String sum;
}
