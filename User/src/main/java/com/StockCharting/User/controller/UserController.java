package com.StockCharting.User.controller;

import com.StockCharting.User.dto.UserDTO;
import com.StockCharting.User.exception.UserNotFoundException;
import com.StockCharting.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.saveUser(userDTO));
    }

    @GetMapping("/search")
    public ResponseEntity<?> findUserById(@RequestParam String userId) throws UserNotFoundException {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @GetMapping("/searchByName")
    public ResponseEntity<?> findUserByName(@RequestParam String username) throws UserNotFoundException {
        return ResponseEntity.ok(userService.findUserByName(username));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestParam String userId,@RequestBody UserDTO newFields) throws UserNotFoundException {
        return ResponseEntity.ok(userService.updateUser(userId,newFields));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam String userId) throws UserNotFoundException {
        userService.deleteUser(userId);
        return (ResponseEntity<?>) ResponseEntity.noContent();
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/verify")
    public ResponseEntity<?> getVerifiedUser(@RequestParam String username,@RequestParam String password) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getVerifiedUser(username,password));
    }
}
