package edu.cources.plannote.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "account_status")
public class AccountStatusEntity {
    @Id
    @Column(name = "account_status_id")
    private String accountStatusId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountStatus")
    private Set<UserEntity> users;
}
