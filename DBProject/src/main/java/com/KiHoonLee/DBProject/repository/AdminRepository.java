package com.KiHoonLee.DBProject.repository;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.admin.UserInfoDto;
import com.KiHoonLee.DBProject.table.Board;
import com.KiHoonLee.DBProject.table.User;
import org.springframework.beans.factory.annotation.Autowired;
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
        jdbcTemplate.update("Delete from s_user\n" +
                "where id= ?;",body.get("id"));
        return new IsSuccessDto(true);
    }
}
