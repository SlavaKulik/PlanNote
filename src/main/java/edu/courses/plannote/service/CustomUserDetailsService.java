package edu.courses.plannote.service;

import edu.courses.plannote.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface CustomUserDetailsService extends UserDetailsService {
    void addNewUser(UserDto user);

    List<UserDto> userList();

    void updateUserByAdmin(UserDto user);

    List<UserDto> getProjectsByUserId(UUID userId);
}
