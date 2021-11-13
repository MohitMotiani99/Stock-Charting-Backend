package com.StockCharting.User.service;

import com.StockCharting.User.dto.UserDTO;
import com.StockCharting.User.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);

    UserDTO findUserById(String userId) throws UserNotFoundException;

    UserDTO findUserByName(String username) throws UserNotFoundException;

    UserDTO updateUser(String userId, UserDTO newFields) throws UserNotFoundException;

    void deleteUser(String userId) throws UserNotFoundException;

    List<UserDTO> getAllUsers();

    UserDTO getVerifiedUser(String username, String password) throws UserNotFoundException;
}
