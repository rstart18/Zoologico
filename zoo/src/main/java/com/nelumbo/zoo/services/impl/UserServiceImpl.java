package com.nelumbo.zoo.services.impl;

import com.nelumbo.zoo.dtos.UserDTO;
import com.nelumbo.zoo.entities.UserEntity;
import com.nelumbo.zoo.repositories.UserRepository;
import com.nelumbo.zoo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public List<UserDTO> getUsers() {
        List<UserEntity> userEntities = (List<UserEntity>) userRepository.findAll();
        List<UserDTO> userDTOs = userEntities.stream()
                .map(userEntity ->
                        UserDTO.builder()
                                .id(userEntity.getId())
                                .name(userEntity.getName())
                                .email(userEntity.getEmail())
                                .pass(userEntity.getPass())
                                .role(userEntity.getRole())
                                .build()
                )
                .collect(Collectors.toList());
        return userDTOs;
    }


    public UserDTO saveUser (UserDTO userDTO) {
        UserEntity userEntity = UserEntity
                .builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .pass(new BCryptPasswordEncoder().encode(userDTO.getPass()))
                .role(userDTO.getRole())
                .build();
        userRepository.save(userEntity);
        userDTO.setId(userEntity.getId());
        return userDTO;
    }

    public boolean deleteUser (Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public void createUsers() {
        UserEntity user1 = UserEntity
                .builder()
                .id(1L)
                .name("admin")
                .email("admin@mail.com")
                .pass(new BCryptPasswordEncoder().encode("admin"))
                .role("ADMIN")
                .build();

        UserEntity user2 = UserEntity
                .builder()
                .id(2L)
                .name("employee")
                .email("employee@mail.com")
                .pass(new BCryptPasswordEncoder().encode("employee"))
                .role("EMPLOYEE")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
    }
}
