package com.KiHoonLee.DBProject.controller;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.admin.UserInfoDto;
import com.KiHoonLee.DBProject.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //유저정보 출력
    @GetMapping
    public ResponseEntity<?> getUserInfo() {
        List<UserInfoDto> userInfoDto = adminService.getUserInfo();
        return new ResponseEntity<>(userInfoDto, HttpStatus.OK);
    }
    
    //유저 삭제
    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody Map<String,String> body) {
        IsSuccessDto isSuccessDto = adminService.deleteUser(body);
        return new ResponseEntity<>(isSuccessDto, HttpStatus.OK);
    }
}
