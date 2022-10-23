package edu.cources.plannote.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "score_list")
public class ScoreEntity {
    @Id
    @Column(name = "score_id")
    private String score;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userScore")
    private Set<UserEntity> users;
}
