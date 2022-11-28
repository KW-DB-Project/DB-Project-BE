package com.KiHoonLee.DBProject.repository;

import com.KiHoonLee.DBProject.dto.user.DepositReceivedDto;
import com.KiHoonLee.DBProject.dto.user.MyInterestDto;
import com.KiHoonLee.DBProject.dto.user.MyStockDto;
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
                        "WHERE ID=? AND PW=?", rowMapper, idPassword.getId(), idPassword.getPw());
        return user;
    }
    public IsSuccessDto insertUser(UserDto userDto) throws DuplicateKeyException {
        jdbcTemplate.update("insert into s_user " +
                                "values(?,?,?,?,?)", userDto.getId(),userDto.getPw(),userDto.getUserName(),userDto.getAge(),0);
        return new IsSuccessDto(true);
    }

    //예수금 조회
    public DepositReceivedDto findDepositReceived(Map<String,String> body) {
        var rowMapper = BeanPropertyRowMapper.newInstance(DepositReceivedDto.class);
        DepositReceivedDto depositReceivedDto = jdbcTemplate.queryForObject(
                    "select id,balance " +
                        "from s_user " +
                        "where id=?",rowMapper,body.get("id"));
        return depositReceivedDto;
    }

    //예수금 충전
    public IsSuccessDto modifyDepositReceived(Map<String,String> body) {
        jdbcTemplate.update(
                "update s_user\n" +
                    "set balance = balance + ?\n" +
                    "where id = ?",body.get("balance"),body.get("id"));
        return new IsSuccessDto(true);
    }

    //관심 종목 조회
    public List<MyInterestDto> findMyInteresting(Map<String,String> body) {
        var rowMapper = BeanPropertyRowMapper.newInstance(MyInterestDto.class);
        List<MyInterestDto>myInterestDto = jdbcTemplate.query(
                "select s.STK_NM, q.S_LAST \n" +
                    "from stock s, stock_quote q, watchlist w\n" +
                    "where Date(s_date)='2022-10-28' and w.S_USER_ID=? \n" +
                    "and s.STK_CD=q.STOCK_STK_CD  and s.STK_CD=w.STOCK_STK_CD\n" +
                    "order by q.S_LAST desc \n",rowMapper,body.get("id"));
        return myInterestDto;
    }

    //작성글 조회
    public List<MyWritingDto> findMyWriting(Map<String,String> body) {
        var rowMapper = BeanPropertyRowMapper.newInstance(MyWritingDto.class);
        List<MyWritingDto> myWritingDto = jdbcTemplate.query(
                        "SELECT b.idx, s.STK_NM, b.title, b.create_date, b.b_like\n" +
                            "from board b, stock s \n" +
                            "where USER_ID = ? and b.stock_stk_cd = s.STK_CD ",rowMapper,body.get("id"));
        return myWritingDto;
    }
    
    //총 수익률
    public float findRateOfReturn(Map<String,String> body){
        float rateOfReturn = jdbcTemplate.queryForObject(
                "with my_stock as (select h.user_id, h.gain_loss as gl,sum(t.buying * t.stk_num) as buying \n" +
                    "from holdingstock h, transaction_description t\n" +
                    "where t.stock_stk_cd=h.stock_stk_cd and t.user_id = h.user_id\n" +
                    "group by h.user_id, h.gain_loss\n" +
                    "having h.user_id=?)\n" +
                    "select sum(my_stock.gl)*100/sum(my_stock.buying) as rate_of_return\n" +
                    "from my_stock",Float.class,body.get("id"));
        return rateOfReturn;
    }
    
    //총 평가금액
    public int findAppraisalAmount(Map<String,String> body) {
        int appraisalAmount = jdbcTemplate.queryForObject(
                "select sum(q.s_last*h.stk_num) as appraisal_amount\n" +
                    "from holdingstock h, stock_quote q\n" +
                    "where h.stock_stk_cd=q.STOCK_STK_CD and Date(q.s_date)='2022-10-28'and h.user_id=?\n",Integer.class,body.get("id"));
        return appraisalAmount;
    }
    
    //보유 주식
    public List<MyStockDto> findMyStockDto(Map<String,String> body) {
        var rowMapper = BeanPropertyRowMapper.newInstance(MyStockDto.class);
        List<MyStockDto> myStockDto = jdbcTemplate.query(
                    "select h.user_id,s.STK_NM,h.average_price,h.stk_num,h.gain_loss\n" +
                        "FROM holdingstock h, stock s\n" +
                        "where h.user_id=? and s.STK_CD=h.stock_stk_cd;",rowMapper,body.get("id"));
        return myStockDto;
    }

}
