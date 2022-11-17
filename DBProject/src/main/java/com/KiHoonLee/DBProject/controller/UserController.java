package com.KiHoonLee.DBProject.controller;

import com.KiHoonLee.DBProject.repository.UserRepository;
import com.KiHoonLee.DBProject.service.UserService;
import com.KiHoonLee.DBProject.table.IdPassword;
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
//    @PostMapping(path = "/map")
//    public String mapRequest(@RequestBody HashMap<String, Object> param){
//        System.out.println("param : " + param);
//        return param.toString();
//    }
    /*


     */
}
