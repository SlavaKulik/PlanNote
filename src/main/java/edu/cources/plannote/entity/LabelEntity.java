package edu.cources.plannote.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "label_list")
public class LabelEntity {
    @Id
    @Column(name = "label_id")
    private String labelName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskLabel")
    private Set<TaskEntity> tasks;
}
