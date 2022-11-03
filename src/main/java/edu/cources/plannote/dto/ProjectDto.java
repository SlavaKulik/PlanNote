package edu.cources.plannote.dto;

import edu.cources.plannote.entity.TaskEntity;
import edu.cources.plannote.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
public class ProjectDto {
    private String id;

    private String projectName;

    private Set<UserEntity> users;

    private UserEntity user;

    private Set<TaskEntity> tasks;
}
