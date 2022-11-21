package edu.cources.plannote.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_list")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID identifier;

    @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$",
            message = "username must be of 4 to 12 length with no special characters")
    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_password")
    private String userPassword;

    @NotBlank(message = "User position should be specified")
    @Column(name = "user_position")
    private String userPosition;

    @Column(name = "user_role")
    private String userRole;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private AccountStatusEntity accountStatus;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_score_id")
    private ScoreEntity userScore;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_status_id")
    private UserStatusEntity userStatus;

    @ManyToMany(mappedBy = "users")
    private List<ProjectEntity> projects;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userTask")
    private Set<TaskEntity> tasks;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userRole));
        return authorities;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
