package com.example.piepaas_api.security.controller;

import com.example.piepaas_api.security.mapper.UserMapper;
import com.example.piepaas_api.security.model.MyUserDetails;
import com.example.piepaas_api.security.entity.User;
import com.example.piepaas_api.security.dto.UserDto;
import com.example.piepaas_api.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin
public  class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = {"/login"})
    public UserDto getUserLoginDetails(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return copyUser(authentication, request);
    }

    @GetMapping(value = {"/", "/home"})
    public UserDto validateUserSession(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // If NOT anonymous user, get user info
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return copyUser(authentication, request);
        }
        return null;
    }


    private UserDto copyUser(Authentication authentication, HttpServletRequest request) {
        try {
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            User user = userRepository.findByUsername(myUserDetails.getUsername());
            String token = request.getSession(false).getId();
            UserDto userDto = userMapper.userToDto(user);
            userDto.setToken(token);
            return userDto;
        } catch (Exception e) {
            logger.warn("Exception: Failed to get Authentication Object=> {}", e.getMessage());
        }
        return null;
    }

}