package edu.cources.plannote.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "subtask_list")
public class SubtaskEntity extends TaskEntity{
    @Column(name = "subtask_name")
    private String subtaskName;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "subtask_task_id")
    private TaskEntity taskSubtask;
}
