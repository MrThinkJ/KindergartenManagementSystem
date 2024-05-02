package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.payload.ChildDTO;
import com.group3.kindergartenmanagementsystem.payload.UserCreateDTO;
import com.group3.kindergartenmanagementsystem.payload.UserDTO;
import com.group3.kindergartenmanagementsystem.utils.ReceivedRole;

public interface UserService {
    UserDTO getUserById(Integer id);
    UserDTO updateUserById(Integer id, UserDTO userDTO);
    UserDTO createNewUser(UserDTO userDTO);
    void deleteUserById(Integer id);
}