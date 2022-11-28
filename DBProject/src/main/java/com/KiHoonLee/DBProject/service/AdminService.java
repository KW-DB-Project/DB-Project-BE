package com.KiHoonLee.DBProject.service;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.admin.UserInfoDto;
import com.KiHoonLee.DBProject.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    //유저 조회
    public List<UserInfoDto> getUserInfo(){
        List<UserInfoDto> userInfoDto = adminRepository.findUserInfo();
        return userInfoDto;
    }
    //유저 삭제
    public IsSuccessDto deleteUser(Map<String,String> body){
        IsSuccessDto isSuccessDto = adminRepository.deleteUser(body);
        return isSuccessDto;
    }
}
