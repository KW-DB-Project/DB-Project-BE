package com.KiHoonLee.DBProject.repository;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.PostDto;
import com.KiHoonLee.DBProject.table.Board;
import com.KiHoonLee.DBProject.table.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;

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
}
