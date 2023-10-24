package com.nelumbo.zoo.services;

import com.nelumbo.zoo.dtos.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserDTO> getUsers();

    UserDTO getUserById(Long id);

    UserDTO saveUser (UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO updatedUser);

    void deleteUser (Long id);

    void createUsers();
}
