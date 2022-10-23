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
    @GeneratedValue
    @Column(name = "id")
    private UUID labelId;

    @Column(name = "label_name")
    private String labelName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskLabel")
    private Set<TaskEntity> tasks;
}
