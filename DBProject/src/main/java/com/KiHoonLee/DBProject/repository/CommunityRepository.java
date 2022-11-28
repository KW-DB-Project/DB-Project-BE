package com.KiHoonLee.DBProject.repository;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.PostDto;
import com.KiHoonLee.DBProject.table.Board;
import com.KiHoonLee.DBProject.table.PostLikeInfo;
import com.KiHoonLee.DBProject.table.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

@Repository
public class CommunityRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //게시글 저장
    public IsSuccessDto insertPost(Board board) throws DataIntegrityViolationException {
        String userId = board.getUserId();
        String stockStkCd = board.getStockStkCd();
        String title = board.getTitle();
        String content = board.getContent();
        int bLike = board.getBLike();

        jdbcTemplate.update("INSERT INTO BOARD (`USER_ID`, `STOCK_STK_CD`, `TITLE`, `CONTENT`, `CREATE_DATE`, `B_LIKE`) VALUES (?,?,?,?,now(),?)"
                , userId, stockStkCd, title, content, bLike);

        return new IsSuccessDto(true);
    }

    //주식 이름을 통해 주식코드를 찾음
    public String findStockCodeByStockName(String name) throws EmptyResultDataAccessException {
        String id = jdbcTemplate.queryForObject(
                "SELECT STK_CD\n" +
                "FROM STOCK\n" +
                "WHERE STK_NM = ?"
                , String.class, name);
        return id;
    }
    //주식코드에 해당하는 게시글 찾음
    public List<Board> findPostsByStockCode(String stockCode) throws EmptyResultDataAccessException {
        var rowMapper = BeanPropertyRowMapper.newInstance(Board.class);

        List<Board> boards = jdbcTemplate.query(
                "SELECT *\n" +
                "FROM BOARD\n" +
                "WHERE STOCK_STK_CD = ?\n" +
                "ORDER BY CREATE_DATE DESC"
                , rowMapper, stockCode
        );
        return boards;
    }
    //좋아요 1증가 이미 누른적이 있으면 그대로
    public void updateUpLike(PostLikeInfo postLikeInfo) {
        //like + 1
        jdbcTemplate.update(
                "UPDATE BOARD\n" +
                "SET B_LIKE = B_LIKE + 1\n" +
                "WHERE IDX = ? AND NOT EXISTS(SELECT * FROM POSTLIKE_INFO WHERE POST_IDX = ? AND USER_ID = ?)",
                postLikeInfo.getPostIdx(), postLikeInfo.getPostIdx(), postLikeInfo.getUserId());
    }

    //좋아요 개수 찾음
    public int findLikeCount(int idx) throws EmptyResultDataAccessException{
        int like = jdbcTemplate.queryForObject(
                "SELECT B_LIKE\n" +
                "FROM BOARD\n" +
                "WHERE IDX = ?"
                , Integer.class, idx);

        return like;
    }
    //사용자 등록
    public void insertPostLikeUser(PostLikeInfo postLikeInfo) {
        var rowMapper = BeanPropertyRowMapper.newInstance(PostLikeInfo.class);
        jdbcTemplate.update(
                "INSERT INTO POSTLIKE_INFO (POST_IDX, USER_ID)\n" +
                "SELECT ?, ?\n" +
                "FROM DUAL\n" +
                "WHERE NOT EXISTS (SELECT * FROM POSTLIKE_INFO WHERE POST_IDX=? AND USER_ID=?)",
                postLikeInfo.getPostIdx(), postLikeInfo.getUserId(), postLikeInfo.getPostIdx(), postLikeInfo.getUserId());
    }
    //게시글에 조아요 누른 사람을 전부 삭제한다
    public void deletePostLikeUser(int idx) {
        jdbcTemplate.update(
                "DELETE FROM POSTLIKE_INFO\n" +
                "WHERE POST_IDX=?",
                idx);
    }
    //게시글 삭제
    public void deletePost(int idx) {
        jdbcTemplate.update(
                "DELETE FROM BOARD\n" +
                "WHERE IDX=?", idx);
    }
}
