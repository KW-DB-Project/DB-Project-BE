package com.KiHoonLee.DBProject.repository;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.trade.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TradingRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //기업 종가 최고가 등
    public StockPriceDto findStockPrice(String name) throws EmptyResultDataAccessException {
        var rowMapper = BeanPropertyRowMapper.newInstance(StockPriceDto.class);
        StockPriceDto stockPriceDto=jdbcTemplate.queryForObject(
                "SELECT s.STK_CD, s_Last, s_Open, s_High, s_Low, s_Vol, s_Chg\n" +
                    "FROM stock_quote q Inner join stock s on s.STK_CD = q.STOCK_STK_CD\n" +
                    "where Date(q.s_date) = '2022-10-28' and s.STK_NM =?;",rowMapper,name);
        return stockPriceDto;
    }
    
    //그래프 데이터
    public List<LastPriceDto> findLastPrice(String name)throws EmptyResultDataAccessException{
        var rowMapper = BeanPropertyRowMapper.newInstance(LastPriceDto.class);
        List<LastPriceDto> lastPriceDto = jdbcTemplate.query(
                "SELECT Date(s_date) as day,s_Last\n" +
                    "FROM stock_quote q Inner join stock s on s.STK_CD=q.STOCK_STK_CD\n" +
                    "where s.STK_NM =? \n" +
                    "and case\n" +
                    "when DAYOFWEEK('2022-10-28')=7 then s_date BETWEEN DATE_ADD(DATE_ADD(Date('2022-10-28'),INTERVAL -1 DAY), INTERVAL -2 WEEK) and DATE_ADD(Date('2022-10-28'), INTERVAL -1 DAY)\n" +
                    "when DAYOFWEEK('2022-10-28')=1 then s_date BETWEEN DATE_ADD(DATE_ADD(Date('2022-10-28'),INTERVAL -2 DAY), INTERVAL -2 WEEK) and DATE_ADD(Date('2022-10-28'), INTERVAL -2 DAY)\n" +
                    "else s_date BETWEEN DATE_ADD(Date('2022-10-28'), INTERVAL -2 WEEK) and Date('2022-10-28')\n" +
                    "end;\n",rowMapper,name);
        return lastPriceDto;
    }
    
    //관심종목
    public IsSuccessDto saveInterestStock(Map<String,String>body) throws EmptyResultDataAccessException{
        if(body.get("heart").equals("false")){
            jdbcTemplate.update(
                        "insert into watchlist\n" +
                            "values(?,?);",body.get("id"),body.get("cd"));
            return new IsSuccessDto(true);
        }
        else{
            jdbcTemplate.update(
                    "delete from watchlist\n" +
                        "where S_USER_ID=? and STOCK_STK_CD=?",body.get("id"),body.get("cd"));
            return new IsSuccessDto(false);
        }

    }
    
    //잔고
    public BalanceDto findBalance(Map<String,String>body)throws EmptyResultDataAccessException{
        var rowMapper = BeanPropertyRowMapper.newInstance(BalanceDto.class);
        BalanceDto balanceDto=jdbcTemplate.queryForObject(
                "SELECT U_NM, BALANCE \n" +
                    "FROM s_user\n" +
                    "WHERE id=?;",rowMapper,body.get("id"));
        return balanceDto;
    }
    
    //매수
    public IsSuccessDto insertBuying(TradingDto tradingDto)throws EmptyResultDataAccessException{
        int balance= jdbcTemplate.queryForObject(
                    "select balance\n" +
                        "from s_user\n" +
                        "where id=?",Integer.class,tradingDto.getId());
        if(balance >= tradingDto.getPrice() * tradingDto.getNum()){
            jdbcTemplate.update(
                    "insert into transaction_description(user_id,stock_stk_cd,stk_num,buying,selling)\n" +
                            "values(?, ?, ?, ?, ?);\n",tradingDto.getId(),tradingDto.getCd(),tradingDto.getNum(),tradingDto.getPrice(),0) ;
            jdbcTemplate.update(
                    "insert into holdingstock(user_id,stock_stk_cd,average_price,stk_num,gain_loss)\n" +
                            "with ave as (\n" +
                            "select t.user_id as id, t.stock_stk_cd as cd, (sum(t.buying * t.stk_num) / sum(t.stk_num)) as avg_cos, \n" +
                            "sum(t.stk_num) as num, q.S_LAST as last_price, (q.S_LAST-(sum(t.buying * t.stk_num) / sum(t.stk_num))) as gl\n" +
                            "from transaction_description t, stock s, s_user u, stock_quote q\n" +
                            "where t.stock_stk_cd=s.stk_cd and u.id=t.user_id and s.stk_cd = q.stock_stk_cd and Date(q.s_date)='2022-10-28' and t.buying not in(0)\n" +
                            "group by t.user_id, t.stock_stk_cd, q.S_LAST\n" +
                            "having t.user_id = ? and t.stock_stk_cd = ?)\n" +
                            "select ave.id, ave.cd, ave.avg_cos,ave.num, ave.gl * ? \n" +
                            "from ave \n" +
                            "on duplicate key update average_price = ave.avg_cos,stk_num = stk_num + ?, gain_loss = ave.gl * holdingstock.stk_num;\n",tradingDto.getId(),tradingDto.getCd(),tradingDto.getNum(),tradingDto.getNum());
            jdbcTemplate.update(
                    "update s_user\n" +
                        "set balance=balance - ?\n" +
                        "where id=?",tradingDto.getNum() * tradingDto.getPrice(),tradingDto.getId());
            return new IsSuccessDto(true);
        }
        else{
            return new IsSuccessDto(false);
        }
    }
    //balance
    public MyStockNumDto findNum(Map<String,String>body)throws EmptyResultDataAccessException{
        var rowMapper = BeanPropertyRowMapper.newInstance(MyStockNumDto.class);
        MyStockNumDto myStockNumDto=jdbcTemplate.queryForObject(
                    "SELECT stk_num\n" +
                        "from holdingstock\n" +
                        "where user_id = ? and stock_stk_cd = ?;",rowMapper,body.get("id"),body.get("cd"));
        return myStockNumDto;
    }

    public IsSuccessDto deleteTrading(TradingDto tradingDto)throws EmptyResultDataAccessException{
        int stkNum = jdbcTemplate.queryForObject(
                    "select stk_num\n" +
                        "from holdingstock\n" +
                        "where user_id=? and stock_stk_cd=?",Integer.class,tradingDto.getId(),tradingDto.getCd());
        if(stkNum >= tradingDto.getNum()){
            //거래내역 추가
            jdbcTemplate.update(
                    "insert into transaction_description(user_id,stock_stk_cd,stk_num,buying,selling)\n" +
                            "values(?, ?, ?, ?, ?);\n", tradingDto.getId(), tradingDto.getCd(), tradingDto.getNum(), 0, tradingDto.getPrice()) ;
            //holdingstock 주식 수 제거
            jdbcTemplate.update(
                    "update holdingstock\n" +
                        "set stk_num = stk_num - ?\n" +
                        "where user_id = ? and stock_stk_cd = ? ",tradingDto.getNum(),tradingDto.getId(),tradingDto.getCd());
            //잔고 업데이트
            jdbcTemplate.update(
                        "update s_user\n" +
                            "set balance = balance + ?\n" +
                            "where id = ?",tradingDto.getNum() * tradingDto.getPrice(),tradingDto.getId());
            return new IsSuccessDto(true);
        }
        else{
            return new IsSuccessDto(false);
        }
    }
}
