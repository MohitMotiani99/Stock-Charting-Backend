package com.StockCharting.User.service;

import com.StockCharting.User.dto.UserDTO;
import com.StockCharting.User.exception.UserNotFoundException;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);

    UserDTO findUserById(String userId) throws UserNotFoundException;

    UserDTO findUserByName(String username) throws UserNotFoundException;

    UserDTO updateUser(String userId, UserDTO newFields) throws UserNotFoundException;

    void deleteUser(String userId) throws UserNotFoundException;
}
