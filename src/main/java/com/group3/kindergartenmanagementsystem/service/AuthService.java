package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.payload.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
