package com.library.service;

import com.library.dto.LoginRequest;
import com.library.dto.LoginResponse;
import com.library.entity.User;

public interface UserService {

    LoginResponse login(LoginRequest request);

    User register(LoginRequest request);

    User findByUsername(String username);

}