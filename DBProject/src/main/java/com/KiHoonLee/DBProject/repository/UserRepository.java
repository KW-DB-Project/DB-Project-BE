package com.KiHoonLee.DBProject.repository;

import com.KiHoonLee.DBProject.dto.user.DepositReceivedDto;
import com.KiHoonLee.DBProject.dto.user.MyInterestDto;
import com.KiHoonLee.DBProject.dto.user.MyWritingDto;
import com.KiHoonLee.DBProject.table.IdPassword;
import com.KiHoonLee.DBProject.table.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.UserDto;
import org.springframework.dao.DuplicateKeyException;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User findByIdAndPassword(IdPassword idPassword) throws EmptyResultDataAccessException {
        var rowMapper = BeanPropertyRowMapper.newInstance(User.class);
        User user = jdbcTemplate.queryForObject(
                "SELECT *\n" +
                        "FROM S_USER\n" +
                        "WHERE ID=? AND PW=?"
                , rowMapper, idPassword.getId(), idPassword.getPw());
        return user;
    }
    public IsSuccessDto insertUser(UserDto userDto) throws DuplicateKeyException {
        jdbcTemplate.update("insert into s_user values(?,?,?,?,?)",userDto.getId(),userDto.getPw(),userDto.getUserName(),userDto.getAge(),0);
        return new IsSuccessDto(true);
    }

    //예수금 조회
    public DepositReceivedDto findDepositReceived(Map<String,String> body) {
        var rowMapper = BeanPropertyRowMapper.newInstance(DepositReceivedDto.class);
        DepositReceivedDto depositReceivedDto = jdbcTemplate.queryForObject("select id,balance from s_user where id=?",rowMapper,body.get("id"));
        return depositReceivedDto;
    }
    //관심종목
    public List<MyInterestDto> findMyInteresting(Map<String,String> body) {
        var rowMapper = BeanPropertyRowMapper.newInstance(MyInterestDto.class);
        List<MyInterestDto>myInterestDto = jdbcTemplate.query("select s.STK_NM, q.S_LAST \n" +
                "from stock s, stock_quote q, watchlist w\n" +
                "where Date(s_date)='2022-10-28' and w.S_USER_ID=? \n" +
                "and s.STK_CD=q.STOCK_STK_CD  and s.STK_CD=w.STOCK_STK_CD\n" +
                "order by q.S_LAST desc \n",rowMapper,body.get("id"));
        return myInterestDto;
    }
    //작성글
    public List<MyWritingDto> findMyWriting(Map<String,String> body) {
        var rowMapper = BeanPropertyRowMapper.newInstance(MyWritingDto.class);
        List<MyWritingDto> myWritingDto = jdbcTemplate.query("SELECT title,create_date,b_like from board where USER_ID=?",rowMapper,body.get("id"));
        return myWritingDto;
    }
//    //손익률 업데이트
//    public IsSuccessDto insertUser(Map<String,String> body) throws NullPointerException {
//        jdbcTemplate.update("insert into s_user values(?,?,?,?,?)",userDto.getId(),userDto.getPw(),userDto.getUserName(),userDto.getAge(),0);
//        return new IsSuccessDto(true);
//    }
}
