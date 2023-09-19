package com.nelumbo.zoo.services;

import com.nelumbo.zoo.dtos.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserDTO> getUsers();

    UserDTO saveUser (UserDTO userDTO);

    boolean deleteUser (Long id);

    void createUsers();
}
