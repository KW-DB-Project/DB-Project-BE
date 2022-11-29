package com.KiHoonLee.DBProject.service;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.admin.EnterpriseInfoDto;
import com.KiHoonLee.DBProject.dto.admin.UserInfoDto;
import com.KiHoonLee.DBProject.dto.community.BoardIsLikeDto;
import com.KiHoonLee.DBProject.dto.community.StkNameUseridDto;
import com.KiHoonLee.DBProject.repository.AdminRepository;
import com.KiHoonLee.DBProject.repository.CommunityRepository;
import com.KiHoonLee.DBProject.table.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private CommunityRepository communityRepository;

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

    //주식회사 조회
    public List<EnterpriseInfoDto> getEnterpriseInfo(){
        List<EnterpriseInfoDto> enterpriseInfoDto = adminRepository.findEnterpriseInfo();
        return enterpriseInfoDto;
    }

    //주식회사 등록
    public IsSuccessDto insertEnterprise(Map<String,String> body){
        try{
            IsSuccessDto isSuccessDto = adminRepository.saveEnterprise(body);
            return isSuccessDto;
        }
        catch (NullPointerException e){
            return new IsSuccessDto(false);
        }

    }

    //해당기업의 게시판 글들을 가져옴
    public List<Board> getPosts(String stockName) {
        List<Board> boards;
        try {
            //주식 코드를 찾음
            String stockCode = communityRepository.findStockCodeByStockName(stockName);
            //해당 주식 코드를 가지는 게시글 리스트를 찾음
            boards = communityRepository.findPostsByStockCode(stockCode);
        } catch (EmptyResultDataAccessException e) {
            boards = null;
        }
        return boards;
    }
}
