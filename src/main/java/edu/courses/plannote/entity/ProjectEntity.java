package edu.courses.plannote.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project_list")
public class ProjectEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID projectId;

    @NotBlank(message = "Project name is mandatory!")
    @Column(name = "project_name")
    private String projectName;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(name = "user_project_list",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectTask")
    private Set<TaskEntity> tasks;
}
