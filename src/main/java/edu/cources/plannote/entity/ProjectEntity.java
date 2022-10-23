package edu.cources.plannote.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "project_list")
public class ProjectEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID projectId;

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
