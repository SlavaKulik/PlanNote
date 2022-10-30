package edu.cources.plannote.service;

import edu.cources.plannote.entity.UserEntity;

import java.util.List;

public interface CustomUserDetailsService {
    List<UserEntity> userList();

    void addNewUser(UserEntity transaction);
}
