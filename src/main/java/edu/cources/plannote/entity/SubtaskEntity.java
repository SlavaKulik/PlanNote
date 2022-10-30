package edu.cources.plannote.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "subtask_list")
public class SubtaskEntity {
    @Id
    @GeneratedValue
    @Column(name = "subtask_id")
    private UUID subtaskId;

    @Column(name = "subtask_name")
    private String subtaskName;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "subtask_task_id")
    private TaskEntity taskSubtask;

    @Column(name = "subtask_time_start")
    private Instant subtaskTimeStart;

    @Column(name = "subtask_time_end")
    private Instant subtaskTimeEnd;
}
