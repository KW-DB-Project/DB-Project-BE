package com.KiHoonLee.DBProject.controller;

import com.KiHoonLee.DBProject.service.UserService;
import com.KiHoonLee.DBProject.table.IdPassword;
import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.UserDto;
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

    //로그인
    @PostMapping("/user/login")
    public ResponseEntity<?> authorizeUser(@RequestBody IdPassword idPassword) {
        return new ResponseEntity<>(userService.isMember(idPassword), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserDto user){
        IsSuccessDto isSuccessDto=userService.saveUser(user);
        return new ResponseEntity<>(isSuccessDto, HttpStatus.OK);
    }
}
