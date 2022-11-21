package edu.cources.plannote.service;

import edu.cources.plannote.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface CustomUserDetailsService extends UserDetailsService {
    void addNewUser(UserDto user);

    List<UserDto> getProjectsByUserId(UUID userId);

}
