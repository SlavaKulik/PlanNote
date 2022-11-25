package edu.courses.plannote.dto;

import edu.courses.plannote.entity.TaskEntity;
import edu.courses.plannote.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

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
