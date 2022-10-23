package edu.cources.plannote.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user_status_list")
public class UserStatusEntity {
    @Id
    @Column(name = "user_status_id")
    private String userStatusId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userStatus")
    private Set<UserEntity> users;
}
