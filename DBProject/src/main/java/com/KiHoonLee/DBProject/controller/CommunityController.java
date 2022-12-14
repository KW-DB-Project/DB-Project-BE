package com.KiHoonLee.DBProject.controller;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.community.BoardIsLikeDto;
import com.KiHoonLee.DBProject.dto.community.PostDto;
import com.KiHoonLee.DBProject.dto.community.StkNameUseridDto;
import com.KiHoonLee.DBProject.service.CommunityService;
import com.KiHoonLee.DBProject.table.Board;
import com.KiHoonLee.DBProject.table.PostLikeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/community")
public class CommunityController {
    @Autowired
    private CommunityService communityService;

    //게시글 작성
    @PostMapping("/write")
    public ResponseEntity<?> writePost(@RequestBody PostDto postDto) {
        IsSuccessDto isSuccessDto = communityService.writePost(postDto);
        return new ResponseEntity<>(isSuccessDto, HttpStatus.OK);
    }

    //게시글 출력
    @PostMapping("/print")
    public ResponseEntity<?> writePost(@RequestBody StkNameUseridDto stockNameUseridDto) {
        List<BoardIsLikeDto> boardIsLikeDtos = communityService.printPost(stockNameUseridDto);
        return new ResponseEntity<>(boardIsLikeDtos, HttpStatus.OK);
    }

    //게시글 삭제
    @PostMapping("/postDelete")
    public ResponseEntity<?> deletePost(@RequestBody Map<String, Integer> body) {
        IsSuccessDto isSuccessDto = communityService.deletePost(body.get("idx"));
        return new ResponseEntity<>(isSuccessDto, HttpStatus.OK);
    }

    //좋아요 증가
    @PostMapping("/like")
    public ResponseEntity<?> likeUp(@RequestBody PostLikeInfo postLikeInfo) {
        Map<String, Integer> responseBody = new HashMap<String, Integer>();
        int likeNum = communityService.likeUp(postLikeInfo);

        responseBody.put("likeCount", likeNum);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
