package edu.courses.plannote.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task_list")
public class TaskEntity {
    @Id
    @GeneratedValue
    @Column(name = "task_id")
    private UUID taskId;

    @NotBlank(message = "Task name is mandatory")
    @Column(name = "task_name")
    private String taskName;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_task_id")
    private ProjectEntity projectTask;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_task_id")
    private UserEntity userTask;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "task_status_id")
    private StatusEntity taskStatus;

    @Column(name = "task_time_end")
    private LocalDateTime taskTimeEnd;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_task_id")
    private PriorityEntity taskPriority;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskTransaction")
    private List<TransactionEntity> transactions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskSubtask")
    private Set<SubtaskEntity> subtasks;
}
