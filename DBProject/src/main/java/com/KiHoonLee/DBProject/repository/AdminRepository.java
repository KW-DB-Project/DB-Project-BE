package com.KiHoonLee.DBProject.repository;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.admin.EnterpriseInfoDto;
import com.KiHoonLee.DBProject.dto.admin.EnterpriseInfoToUpdateDto;
import com.KiHoonLee.DBProject.dto.admin.UserInfoDto;
import com.KiHoonLee.DBProject.table.Board;
import com.KiHoonLee.DBProject.table.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AdminRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    //유저 정보 조회
    public List<UserInfoDto> findUserInfo(){
        var rowMapper = BeanPropertyRowMapper.newInstance(UserInfoDto.class);
        List<UserInfoDto> userInfoDto = jdbcTemplate.query(
                "SELECT id, u_nm, age FROM db.s_user\n" +
                    "where id !='admin';",rowMapper);
        return userInfoDto;
    }
    //유저 삭제
    public IsSuccessDto deleteUser(Map<String,String>body){
        jdbcTemplate.update("Delete from postlike_info\n" +
                "where user_id= ?;",body.get("id"));
        jdbcTemplate.update("Delete from transaction_description\n" +
                "where user_id= ?;",body.get("id"));
        jdbcTemplate.update("Delete from board\n" +
                "where USER_ID= ?;",body.get("id"));
        jdbcTemplate.update("Delete from holdingstock\n" +
                "where user_id= ?;",body.get("id"));
        jdbcTemplate.update("Delete from watchlist\n" +
                "where S_USER_ID= ?;",body.get("id"));
        jdbcTemplate.update("Delete from s_user\n" +
                "where ID= ?;",body.get("id"));
        return new IsSuccessDto(true);
    }

    //유저 삭제
    public IsSuccessDto deleteEnterprise(Map<String,String>body){
        jdbcTemplate.update("Delete from stockholder\n" +
                "where STOCK_STK_CD= ?;",body.get("code"));
        jdbcTemplate.update("Delete from transaction_description\n" +
                "where stock_stk_cd= ?;",body.get("code"));
        jdbcTemplate.update("Delete from board\n" +
                "where STOCK_STK_CD= ?;",body.get("code"));
        jdbcTemplate.update("Delete from holdingstock\n" +
                "where stock_stk_cd= ?;",body.get("code"));
        jdbcTemplate.update("Delete from watchlist\n" +
                "where STOCK_STK_CD= ?;",body.get("code"));
        jdbcTemplate.update("Delete from stock_quote\n" +
                "where STOCK_STK_CD= ?;",body.get("code"));
        jdbcTemplate.update("Delete from enterprise_info\n" +
                "where STOCK_STK_CD= ?;",body.get("code"));
        jdbcTemplate.update("Delete from stock\n" +
                "where STK_CD= ?;",body.get("code"));
        return new IsSuccessDto(true);
    }

    //주식회사 조회
    public List<EnterpriseInfoDto> findEnterpriseInfo(){
        var rowMapper = BeanPropertyRowMapper.newInstance(EnterpriseInfoDto.class);
        List<EnterpriseInfoDto> enterpriseInfoDto = jdbcTemplate.query(
                "SELECT e.ent_nm,e.ENT_SMRY,e.STOCK_STK_CD,s.S_LAST\n" +
                    "FROM enterprise_info e, stock_quote s\n" +
                    "where e.STOCK_STK_CD=s.STOCK_STK_CD and Date(s_date)='2022-10-28';",rowMapper);
        return enterpriseInfoDto;
    }
    
    //주식회사 등록
    public IsSuccessDto saveEnterprise(Map<String,String>body) throws NullPointerException{
        int firstCheck=jdbcTemplate.queryForObject(
                "select exists(\n" +
                    "select STK_NM\n" +
                    "from stock\n" +
                    "where stk_nm=? \n" +
                    ") as firstCheck",Integer.class,body.get("name"));
        int secondCheck=jdbcTemplate.queryForObject(
                "select exists(\n" +
                    "select STK_CD\n" +
                    "from stock\n" +
                    "where stk_cd=? \n" +
                    ") as secondCheck",Integer.class,body.get("code"));
        if (firstCheck==1 || secondCheck==1){ //둘중 하나라도 중복이면 false
            return new IsSuccessDto(false);
        }
        else //이름 코드 중복 없을 때
        {
            jdbcTemplate.update(
                    "insert into stock\n" +
                            "values(?,?);",body.get("code"),body.get("name"));
            jdbcTemplate.update(
                    "insert into enterprise_info\n" +
                    "values(?,?,?,?,?);",body.get("name"),body.get("code"),body.get("count"),body.get("content"),body.get("category"));
            jdbcTemplate.update("insert into stock_quote\n" +
                    "values(?,now(),?,?,?,?,0,0);",body.get("code"),body.get("price"),body.get("price"),body.get("price"),body.get("price"));
            return new IsSuccessDto(true);
        }
    }

    //기업정보 수정
    public IsSuccessDto updateEnterpriseInfo(EnterpriseInfoToUpdateDto modifiedEnterpriseInfoDto) {
        //stock table의 주식이름 수정
        updateStockName(modifiedEnterpriseInfoDto.getStockCode(), modifiedEnterpriseInfoDto.getNewEntNm());
        //기업정보 수정
        jdbcTemplate.update(
                "UPDATE ENTERPRISE_INFO\n" +
                "SET ENT_NM=?, ENT_SMRY=?\n" +
                "WHERE STOCK_STK_CD=? AND ENT_NM=?",
                modifiedEnterpriseInfoDto.getNewEntNm(),
                modifiedEnterpriseInfoDto.getNewEntSmr(),
                modifiedEnterpriseInfoDto.getStockCode(),
                modifiedEnterpriseInfoDto.getOldEntNm()
        );
        return new IsSuccessDto(true);
    }

    //주식이름 수정
    public void updateStockName(String stockCode, String newStockName) {
        //주식이름 수정
        jdbcTemplate.update(
                "UPDATE STOCK\n" +
                "SET STK_NM=?\n" +
                "WHERE STK_CD=?",
                newStockName, stockCode);
    }

    //주식코드를 통해 주식이름을 찾음
    public String findStockNameByStockCode(String stockCode) {
        String stockName = jdbcTemplate.queryForObject(
                            "SELECT STK_NM\n" +
                            "FROM STOCK\n" +
                            "WHERE STK_CD=?",
                            String.class, stockCode);
        return stockName;
    }
}
