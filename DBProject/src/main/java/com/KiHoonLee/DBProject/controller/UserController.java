package com.KiHoonLee.DBProject.controller;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.UserDto;
import com.KiHoonLee.DBProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserDto user){
        IsSuccessDto isSuccessDto=userService.saveUser(user);
        return new ResponseEntity<>(isSuccessDto, HttpStatus.OK);
    }
}
