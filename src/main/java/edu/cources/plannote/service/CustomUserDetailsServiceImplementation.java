package edu.cources.plannote.service;

import edu.cources.plannote.entity.UserEntity;
import edu.cources.plannote.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsServiceImplementation implements CustomUserDetailsService{

    private final UserRepository userRepository;

    public CustomUserDetailsServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> userList() {
        return userRepository.findAll();
    }

    @Override
    public void addNewUser(UserEntity user) { userRepository.save(user); }

}
