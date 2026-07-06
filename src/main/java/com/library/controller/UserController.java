package com.library.controller;

import com.library.dto.LoginRequest;
import com.library.entity.User;
import com.library.service.UserService;
import com.library.utils.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result<Object> login(@RequestBody LoginRequest request) {
        return Result.success(userService.login(request));
    }

    @PostMapping("/register")
    public Result<Object> register(@RequestBody LoginRequest request) {
        User user = userService.register(request);
        return Result.success("注册成功", user);
    }

}