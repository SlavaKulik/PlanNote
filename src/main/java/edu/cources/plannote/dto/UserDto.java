package edu.cources.plannote.dto;

import edu.cources.plannote.entity.*;
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
public class UserDto {
    private String id;

    private String userName;

    private String userPassword;

    private String userPosition;

    private AccountStatusEntity accountStatus;

    private ScoreEntity score;

    private String userScore;

    private String userRole;

    private UserStatusEntity userStatus;

    private Set<ProjectEntity> projects;

    private Set<String> projectName;

    private Set<UUID> projectId;

    private Set<TaskEntity> tasks;
}
