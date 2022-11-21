package edu.cources.plannote.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "priority_list")
public class PriorityEntity {
    @Id
    @Column(name = "priority_id")
    private String priorityId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskPriority")
    private Set<TaskEntity> tasks;
}
