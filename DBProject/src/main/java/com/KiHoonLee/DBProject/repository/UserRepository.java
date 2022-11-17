package com.KiHoonLee.DBProject.repository;

import com.KiHoonLee.DBProject.table.IdPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.UserDto;
import org.springframework.dao.DuplicateKeyException;
@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public IdPassword findByIdAndPassword(IdPassword idPassword) throws EmptyResultDataAccessException {
        var rowMapper = BeanPropertyRowMapper.newInstance(IdPassword.class);
        IdPassword idPasswords = jdbcTemplate.queryForObject(
                "SELECT ID, PW\n" +
                        "FROM S_USER\n" +
                        "WHERE ID=? AND PW=?"
                , rowMapper, idPassword.getId(), idPassword.getPw());

        return idPassword;
    }
    public IsSuccessDto insertUser(UserDto userDto) throws DuplicateKeyException {
        jdbcTemplate.update("insert into s_user values(?,?,?,?,?)",userDto.getId(),userDto.getPw(),userDto.getUserName(),userDto.getAge(),0);
        return new IsSuccessDto(true);
    }
}
