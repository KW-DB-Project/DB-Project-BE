package com.KiHoonLee.DBProject.repository;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public IsSuccessDto insertUser(UserDto userDto) throws DuplicateKeyException {
        jdbcTemplate.update("insert into s_user values(?,?,?,?,?)",userDto.getId(),userDto.getPw(),userDto.getUserName(),userDto.getAge(),0);
        return new IsSuccessDto(true);
    }
}
