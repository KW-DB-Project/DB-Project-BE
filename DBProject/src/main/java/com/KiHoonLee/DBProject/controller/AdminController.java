package com.KiHoonLee.DBProject.controller;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.admin.EnterpriseInfoDto;
import com.KiHoonLee.DBProject.dto.admin.UserInfoDto;
import com.KiHoonLee.DBProject.service.AdminService;
import com.KiHoonLee.DBProject.service.CommunityService;
import com.KiHoonLee.DBProject.table.Board;
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
    @Autowired
    private CommunityService communityService;

    //유저정보 출력
    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo() {
        List<UserInfoDto> userInfoDto = adminService.getUserInfo();
        return new ResponseEntity<>(userInfoDto, HttpStatus.OK);
    }

    //유저 삭제
    @PostMapping("/user/delete")
    public ResponseEntity<?> deleteUser(@RequestBody Map<String, String> body) {
        IsSuccessDto isSuccessDto = adminService.deleteUser(body);
        return new ResponseEntity<>(isSuccessDto, HttpStatus.OK);
    }

    //주식회사 출력
    @GetMapping("/enterprise")
    public ResponseEntity<?> getEnterpriseInfo() {
        List<EnterpriseInfoDto> enterpriseInfoDto = adminService.getEnterpriseInfo();
        return new ResponseEntity<>(enterpriseInfoDto, HttpStatus.OK);
    }

    //주식회사 등록
    @PostMapping("/enterprise/insert")
    public ResponseEntity<?> insertEnterprise(@RequestBody Map<String, String> body) {
        IsSuccessDto isSuccessDto = adminService.insertEnterprise(body);
        return new ResponseEntity<>(isSuccessDto, HttpStatus.OK);
    }

    //기업명으로 토론방 게시글 검색
    @PostMapping("/community/searchPosts")
    public ResponseEntity<?> getPostsInfo(@RequestBody Map<String, String> body) {
        List<Board> boards = adminService.getPosts(body.get("stockName"));
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    //게시글 삭제 + 해당게시글에 좋아요 누른사람 삭제
    @PostMapping("/community/postDelete")
    public ResponseEntity<?> deletePost(int idx) {
        IsSuccessDto isSuccessDto= communityService.deletePost(idx);
        return new ResponseEntity<>(isSuccessDto, HttpStatus.OK);
    }
}
