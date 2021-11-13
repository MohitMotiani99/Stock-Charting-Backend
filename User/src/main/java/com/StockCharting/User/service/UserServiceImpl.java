package com.StockCharting.User.service;

import com.StockCharting.User.dto.UserDTO;
import com.StockCharting.User.entity.User;
import com.StockCharting.User.exception.UserNotFoundException;
import com.StockCharting.User.mapper.UserMapper;
import com.StockCharting.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        return userMapper.map(userRepository.save(userMapper.map(userDTO, User.class)),UserDTO.class);
    }

    @Override
    public UserDTO findUserById(String userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if(userOptional.isEmpty())
            throw new UserNotFoundException("User Not Available");
        return userMapper.map(userOptional.get(),UserDTO.class);
    }

    @Override
    public UserDTO findUserByName(String username) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty())
            throw new UserNotFoundException("User Not Available");
        return userMapper.map(userOptional.get(),UserDTO.class);
    }

    @Override
    public UserDTO updateUser(String userId, UserDTO newFields) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if(userOptional.isEmpty())
            throw new UserNotFoundException("User Not Available");

        User user = userOptional.get();

        user.setUsername(newFields.getUsername()==null?user.getUsername(): newFields.getUsername());
        user.setPassword(newFields.getPassword()==null?user.getPassword(): newFields.getPassword());
        user.setEmail(newFields.getEmail()==null?user.getEmail(): newFields.getEmail());
        user.setMobile(newFields.getMobile()==null?user.getMobile(): newFields.getMobile());
        user.setConfirmed(newFields.getConfirmed()==null?user.getConfirmed(): newFields.getConfirmed());
        user.setUserType(newFields.getUserType()==null?user.getUserType(): newFields.getUserType());

        return userMapper.map(userRepository.save(user),UserDTO.class);
    }

    @Override
    public void deleteUser(String userId) throws UserNotFoundException {

        Optional<User> userOptional = userRepository.findByUserId(userId);
        if(userOptional.isEmpty())
            throw new UserNotFoundException("User Not Available");

        userRepository.deleteByUserId(userId);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> userMapper.map(user,UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getVerifiedUser(String username, String password) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(username,password);
        if(userOptional.isEmpty())
            throw new UserNotFoundException("Incorrect Username or Password");
        return userMapper.map(userOptional.get(),UserDTO.class);
    }
}
