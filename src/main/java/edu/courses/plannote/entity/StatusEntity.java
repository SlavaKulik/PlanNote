package edu.courses.plannote.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "status_list")
public class StatusEntity {
    @Id
    @Column(name = "status_id")
    private String statusId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskStatus")
    private List<TaskEntity> tasks;
}
