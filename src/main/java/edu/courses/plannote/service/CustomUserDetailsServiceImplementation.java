package edu.courses.plannote.service;

import edu.courses.plannote.dto.UserDto;
import edu.courses.plannote.entity.AccountStatusEntity;
import edu.courses.plannote.entity.ScoreEntity;
import edu.courses.plannote.entity.UserEntity;
import edu.courses.plannote.repository.UserRepository;
import edu.courses.plannote.utils.DtoToEntity;
import edu.courses.plannote.utils.EntityToDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
@Service
public class CustomUserDetailsServiceImplementation implements CustomUserDetailsService{

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsServiceImplementation.class);

    private final UserRepository userRepository;

    public CustomUserDetailsServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> userList() {
        return userRepository.findAll().stream()
                .map(EntityToDto::userEntityToDto)
                .toList();
    }

    @Override
    public void updateUserByAdmin(UserDto user) {
        UserEntity userEntity = userRepository.getReferenceById(UUID.fromString(user.getId()));
        if (Objects.nonNull(user.getUserScore())) {
            ScoreEntity score = new ScoreEntity();
            score.setScore(user.getUserScore());
            userEntity.setUserScore(score);
        } else {
            AccountStatusEntity accountStatus = new AccountStatusEntity();
            accountStatus.setAccountStatusId(user.getAccStatus());
            userEntity.setAccountStatus(accountStatus);
        }
        userRepository.save(userEntity);
        log.info("Updated user");
    }

    @Override
    public void addNewUser(UserDto userDto) {
        UserEntity user = DtoToEntity.userDtoToEntity(userDto);
        userRepository.save(user);
        log.info("Added new user");
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
