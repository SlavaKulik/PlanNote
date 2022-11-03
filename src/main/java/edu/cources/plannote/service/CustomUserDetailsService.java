package edu.cources.plannote.service;

import edu.cources.plannote.dto.ProjectDto;
import edu.cources.plannote.dto.UserDto;
import edu.cources.plannote.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface CustomUserDetailsService extends UserDetailsService {
    List<UserDto> userList();

    void addNewUser(UserDto user);

    List<UserDto> getUsersByName(String name);

    List<UserDto> getProjectsByUserId(UUID userId);

}
