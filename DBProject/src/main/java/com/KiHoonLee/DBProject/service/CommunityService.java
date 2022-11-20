package com.KiHoonLee.DBProject.service;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.PostDto;
import com.KiHoonLee.DBProject.repository.CommunityRepository;
import com.KiHoonLee.DBProject.table.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;

    //작성된 게시글 저장
    public IsSuccessDto writePost(PostDto postDto) {
        IsSuccessDto isSuccessDto;
        try {
            String userId = postDto.getId();
            String stockStkCd = communityRepository.findStockCodeByStockName(postDto.getStockName());
            String title = postDto.getTitle();
            String content = postDto.getContent();

            Board board = new Board(0, userId, stockStkCd, title, content, null, 0);

            isSuccessDto = communityRepository.insertPost(board);

        } catch (EmptyResultDataAccessException | DataIntegrityViolationException e) {
            isSuccessDto = new IsSuccessDto(false);
        }

        return isSuccessDto;
    }

    //게시판 게시글들을 찾음
    public List<Board> printPost(String stockName) {
        List<Board> boards;
        try {
            String stockCode = communityRepository.findStockCodeByStockName(stockName);
            boards = communityRepository.findPostsByStockCode(stockCode);
        } catch (EmptyResultDataAccessException e) {
            boards = null;
        }
        return boards;
    }
}
