package com.group3.kindergartenmanagementsystem.controller;

import com.group3.kindergartenmanagementsystem.payload.UserCreateDTO;
import com.group3.kindergartenmanagementsystem.payload.UserDTO;
import com.group3.kindergartenmanagementsystem.service.ParentService;
import com.group3.kindergartenmanagementsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/parent")
public class ParentController {
    UserService userService;
    ParentService parentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserCreateDTO> createNewParent(@RequestBody UserCreateDTO userCreateDTO){
        return new ResponseEntity<>(parentService.createNewParent(userCreateDTO.getUserDTO(), userCreateDTO.getChildDTO()), HttpStatus.CREATED);
    }
}
