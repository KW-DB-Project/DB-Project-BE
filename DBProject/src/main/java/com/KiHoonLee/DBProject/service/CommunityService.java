package com.KiHoonLee.DBProject.service;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.community.BoardIsLikeDto;
import com.KiHoonLee.DBProject.dto.community.PostDto;
import com.KiHoonLee.DBProject.dto.community.StkNameUseridDto;
import com.KiHoonLee.DBProject.repository.CommunityRepository;
import com.KiHoonLee.DBProject.table.Board;
import com.KiHoonLee.DBProject.table.PostLikeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

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
    public List<BoardIsLikeDto> printPost(StkNameUseridDto stkNameUseridDto) {
        List<Board> boards;
        List<BoardIsLikeDto> boardIsLikeDtos = new ArrayList<>();
        boolean isLike;
        try {
            //주식 코드를 찾음
            String stockCode = communityRepository.findStockCodeByStockName(stkNameUseridDto.getStockName());
            //해당 주식 코드를 가지는 게시글 리스트를 찾음
            boards = communityRepository.findPostsByStockCode(stockCode);

            for (Board board : boards) {
                //해당 게시글에 대해 좋아요를 눌렀는지 찾음
                Boolean isLikePost = communityRepository.findIsLikePost(board.getIdx() , stkNameUseridDto.getUserId());
                boardIsLikeDtos.add(new BoardIsLikeDto(board, isLikePost));
            }

        } catch (EmptyResultDataAccessException e) {
            boardIsLikeDtos = null;
        }
        return boardIsLikeDtos;
    }

    //좋아요 개수 증가
    //에러시 좋아요 수 -1반환
    public int likeUp(PostLikeInfo postLikeInfo) {
        int likeNum = -1;

        //해당 idx가 존재하지 않으면 -1 존재하면 좋아요개수
        try {
                //좋아요 1증가 이미 누른적 있으면 그대로
                communityRepository.updateUpLike(postLikeInfo);
                communityRepository.insertPostLikeUser(postLikeInfo);//해당 게시글 좋아요누린사람 추가
                likeNum = communityRepository.findLikeCount(postLikeInfo.getPostIdx());

        } catch (EmptyResultDataAccessException e) {}

        return likeNum;
    }
    //게시글 삭제 + 해당게시글에 좋아요 누른사람 삭제
    public IsSuccessDto deletePost(int idx) {
        communityRepository.deletePostLikeUser(idx);
        communityRepository.deletePost(idx);
        return new IsSuccessDto(true);
    }
}
