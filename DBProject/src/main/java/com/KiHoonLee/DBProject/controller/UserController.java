package com.KiHoonLee.DBProject.controller;

import com.KiHoonLee.DBProject.dto.user.*;
import com.KiHoonLee.DBProject.service.UserService;
import com.KiHoonLee.DBProject.table.IdPassword;
import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> authorizeUser(@RequestBody IdPassword idPassword) {
        return new ResponseEntity<>(userService.isMember(idPassword), HttpStatus.OK);
    }

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserDto user){
        IsSuccessDto isSuccessDto=userService.saveUser(user);
        return new ResponseEntity<>(isSuccessDto, HttpStatus.OK);
    }

    //예수금
    @PostMapping("/depositReceived")
    public ResponseEntity<?>getDepositReceived(@RequestBody Map<String,String> body){
        DepositReceivedDto depositReceivedDto = userService.getDepositReceived(body);
        return new ResponseEntity<>(depositReceivedDto, HttpStatus.OK);
    }

    //예수금 충전
    @PostMapping("/addDepositReceived")
    public ResponseEntity<?>updateDepositReceived(@RequestBody Map<String,String> body){
        IsSuccessDto isSuccessDto = userService.updateDepositReceived(body);
        return new ResponseEntity<>(isSuccessDto, HttpStatus.OK);
    }

    //관심종목
    @PostMapping("/myInterest")
    public ResponseEntity<?>getMyInterest(@RequestBody Map<String,String> body){
        List<MyInterestDto> myInterestDto = userService.getMyInteresting(body);
        return new ResponseEntity<>(myInterestDto, HttpStatus.OK);
    }

    //작성글
    @PostMapping("/myWriting")
    public ResponseEntity<?>getMyWriting(@RequestBody Map<String,String> body){
        List<MyWritingDto> myWritingDto = userService.getMyWriting(body);
        return new ResponseEntity<>(myWritingDto, HttpStatus.OK);
    }

    //보유 주식
    @PostMapping("/myStock")
    public ResponseEntity<?>getMyStock(@RequestBody Map<String,String> body){
        AllGainDto allGainDto = userService.getMyStock(body);
        return new ResponseEntity<>(allGainDto, HttpStatus.OK);
    }

}
