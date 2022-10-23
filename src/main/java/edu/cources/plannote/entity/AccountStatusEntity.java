package edu.cources.plannote.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "account_status_list")
public class AccountStatusEntity {
    @Id
    @Column(name = "account_status_id")
    private String accountStatusId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountStatus")
    private Set<UserEntity> users;
}
