package com.nelumbo.zoo.services.impl;

import com.nelumbo.zoo.dtos.UserDTO;
import com.nelumbo.zoo.entities.UserEntity;
import com.nelumbo.zoo.repositories.UserRepository;
import com.nelumbo.zoo.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public List<UserDTO> getUsers() {
        List<UserEntity> userEntities = (List<UserEntity>) userRepository.findAll();
        return userEntities.stream()
                .map(userEntity ->
                        UserDTO.builder()
                                .id(userEntity.getId())
                                .name(userEntity.getName())
                                .email(userEntity.getEmail())
                                .role(userEntity.getRole())
                                .build()
                )
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (userEntity.isPresent()) {
            UserEntity user = userEntity.get();
            return UserDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();
        } else {
            throw new NoSuchElementException("Usuario no encontrado.");
        }
    }

    @Transactional
    public UserDTO saveUser(UserDTO userDTO) {

        Optional<UserEntity> existingUser = userRepository.findOneByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("El correo electrónico con que desea registrar ya está en uso.");
        }

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

    @Transactional
    public UserDTO updateUser(Long id, UserDTO updatedUser) {
        Optional<UserEntity> existingUser = userRepository.findOneByEmail(updatedUser.getEmail());

        if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
            throw new IllegalArgumentException("El correo electrónico con que desea registrar ya está en uso.");
        }

        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setName(updatedUser.getName());
            userEntity.setEmail(updatedUser.getEmail());
            userEntity.setRole(updatedUser.getRole());

            if (updatedUser.getPass() != null && !updatedUser.getPass().isEmpty()) {
                String hashedPassword = new BCryptPasswordEncoder().encode(updatedUser.getPass());
                userEntity.setPass(hashedPassword);
            }

            userRepository.save(userEntity);

            return UserDTO.builder()
                    .id(userEntity.getId())
                    .name(userEntity.getName())
                    .email(userEntity.getEmail())
                    .role(userEntity.getRole())
                    .build();
        } else {
            throw new NoSuchElementException("Usuario no encontrado.");
        }
    }


    public void deleteUser(Long id) {
        UserEntity userToDelete = userRepository.findById(id).orElse(null);
        if (userToDelete == null) {
            throw new NoSuchElementException("Usuario no encontrado.");
        }
        if ("ADMIN".equals(userToDelete.getRole())) {
            long adminCount = userRepository.countByRole("ADMIN");
            if (adminCount <= 1) {
                throw new IllegalArgumentException("No se ha podido eliminar el usuario debido a que es el unico" +
                        " administrador registrado.");
            }
        }
        try {
            userRepository.deleteById(id);
        } catch (Exception err) {
            throw new RuntimeException("Error al eliminar el usuario: " + err.toString());
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

        UserEntity user3 = UserEntity
                .builder()
                .id(3L)
                .name("Taringa")
                .email("taringa_01@outlook.com")
                .pass(new BCryptPasswordEncoder().encode("taringa"))
                .role("EMPLOYEE")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
