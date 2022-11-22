package edu.cources.plannote.dto;

import edu.cources.plannote.entity.TaskEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class SubtaskDto {
    private String id;

    private String subtaskName;

    private TaskEntity task;

    private String endTime;
}
