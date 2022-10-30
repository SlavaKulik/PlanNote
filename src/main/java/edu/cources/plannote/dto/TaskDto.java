package edu.cources.plannote.dto;

import edu.cources.plannote.entity.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@Builder
@ToString
public class TaskDto {
    private String id;

    private String taskName;

    private ProjectEntity projectTask;

    private UserEntity userTask;

    private StatusEntity statusTask;

    private String startTime;

    private String endTime;

    private LabelEntity labelTask;

    private PriorityEntity priorityTask;

    private Set<TransactionEntity> transactions;

    private Set<SubtaskEntity> subtasks;
}
