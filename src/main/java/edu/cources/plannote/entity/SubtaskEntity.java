package edu.cources.plannote.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subtask_list")
public class SubtaskEntity {
    @Id
    @GeneratedValue
    @Column(name = "subtask_id")
    private UUID subtaskId;

    @NotBlank(message = "Subtask name is mandatory!")
    @Column(name = "subtask_name")
    private String subtaskName;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "subtask_task_id")
    private TaskEntity taskSubtask;

    @Column(name = "subtask_time_start")
    private LocalDateTime subtaskTimeStart;

    @Column(name = "subtask_time_end")
    private LocalDateTime subtaskTimeEnd;
}
