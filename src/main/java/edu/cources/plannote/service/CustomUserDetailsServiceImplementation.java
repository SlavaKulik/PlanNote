package edu.cources.plannote.service;

import edu.cources.plannote.dto.UserDto;
import edu.cources.plannote.entity.UserEntity;
import edu.cources.plannote.repository.UserRepository;
import edu.cources.plannote.utils.DtoToEntity;
import edu.cources.plannote.utils.EntityToDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomUserDetailsServiceImplementation implements CustomUserDetailsService{

    private final UserRepository userRepository;

    public CustomUserDetailsServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addNewUser(UserDto userDto) {
        UserEntity user = DtoToEntity.userDtoToEntity(userDto);
        userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByUserName(username))
                .orElseThrow(() -> new UsernameNotFoundException("Username was not found!"));
    }

    @Override
    public List<UserDto> getProjectsByUserId(UUID userId) {
        return userRepository.findUserEntitiesByIdentifier(userId)
                .stream()
                .map(EntityToDto::userEntityToDto)
                .toList();
    }
}
