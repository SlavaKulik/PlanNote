package edu.cources.plannote.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Inheritance(
        strategy = InheritanceType.TABLE_PER_CLASS
)
@Table(name = "task_list")
public class TaskEntity {
    @Id
    @GeneratedValue
    @Column(name = "task_id")
    private UUID taskId;

    @Column(name = "task_name")
    private String taskName;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_task_id")
    private ProjectEntity projectTask;

    @Column(name = "task_time_start")
    private Instant taskTimeStart;

    @Column(name = "task_time_end")
    private Instant taskTimeEnd;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "label_task_id")
    private LabelEntity taskLabel;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_task_id")
    private LabelEntity taskPriority;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskTransaction")
    private Set<TransactionEntity> transactions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskSubtask")
    private Set<SubtaskEntity> subtasks;
}
