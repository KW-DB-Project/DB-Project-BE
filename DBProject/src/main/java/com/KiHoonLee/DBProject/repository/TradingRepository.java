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

    //관심종목 하트
    public IsSuccessDto findInterestStock(Map<String,String>body){
        int isCheck=jdbcTemplate.queryForObject(
                "select exists(\n" +
                    "   select s_user_id,stock_stk_cd\n" +
                    "    from watchlist\n" +
                    "    where s_user_id=? and stock_stk_cd=?\n" +
                    ") as isCheck",Integer.class,body.get("id"),body.get("cd"));
        if(isCheck == 1){
            return new IsSuccessDto(true);
        }
        else {
            return new IsSuccessDto(false);
        }
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
        //관심종목 설정
        if(body.get("heart").equals("false")){
            jdbcTemplate.update(
                        "insert into watchlist\n" +
                            "values(?,?);",body.get("id"),body.get("cd"));
            return new IsSuccessDto(true);
        }
        //관심종목 해제
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
        //잔고 조회
        int balance= jdbcTemplate.queryForObject(
                    "select balance\n" +
                        "from s_user\n" +
                        "where id=?",Integer.class,tradingDto.getId());
        //잔고보다 많이 살 수 없음
        if(balance >= tradingDto.getPrice() * tradingDto.getNum()){
            //거래내역 작성
            jdbcTemplate.update(
                    "insert into transaction_description(user_id,stock_stk_cd,stk_num,buying,selling)\n" +
                            "values(?, ?, ?, ?, ?);\n",tradingDto.getId(),tradingDto.getCd(),tradingDto.getNum(),tradingDto.getPrice(),0) ;
            //보유하고 주식인지 체크
            int isCheck=jdbcTemplate.queryForObject(
                        "select exists(\n" +
                            "select user_id,stock_stk_cd\n" +
                            "from holdingstock\n" +
                            "where user_id =? and stock_stk_cd = ?\n" +
                        ") as isCheck",Integer.class,tradingDto.getId(),tradingDto.getCd());
            //holdingstock에 정보 없을 때 인서트
            if(isCheck==0){
                jdbcTemplate.update(
                        "insert into holdingstock(user_id,stock_stk_cd,average_price,stk_num,gain_loss)\n" +
                            "select t.user_id, t.stock_stk_cd, t.buying, t.stk_num, (q.S_LAST-t.buying)*t.stk_num   \n" +
                            "from transaction_description t, stock_quote q\n" +
                            "where t.user_id= ? and t.STOCK_STK_CD = ? and Date(q.s_date)='2022-10-28' \n" +
                            "and q.STOCK_STK_CD= t.STOCK_STK_CD  order by idx desc limit 1",tradingDto.getId(),tradingDto.getCd());
            }
            // 보유하고 있는 주식
            else{
                //보유 주식 업데이트
                jdbcTemplate.update(
                        "update holdingstock result, (select (t.stk_num + h.stk_num) as num,\n" +
                                "((h.average_price * h.stk_num)+(t.buying*t.stk_num))/(t.stk_num + h.stk_num) as avg_cos,\n" +
                                "((q.S_LAST-((h.average_price * h.stk_num)+(t.buying*t.stk_num))/(t.stk_num + h.stk_num))*(t.stk_num + h.stk_num))as gl\n" +
                                "from transaction_description t, stock_quote q, holdingstock h\n" +
                                "where t.stock_stk_cd = h.stock_stk_cd  and t.stock_stk_cd  = q.stock_stk_cd and t.user_id= h.user_id\n" +
                                "and Date(q.s_date)='2022-10-28' and t.buying not in(0) and h.user_id=? and h.STOCK_STK_CD=?\n" +
                                "order by idx desc limit 1) temp\n" +
                                "set result.stk_num=temp.num,result.average_price=temp.avg_cos,result.gain_loss=temp.gl\n" +
                                "where result.user_id=? and result.stock_stk_cd=?",tradingDto.getId(),tradingDto.getCd(),tradingDto.getId(),tradingDto.getCd());
            }
            //잔고 갱신
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

    //balance 잔고 조회
    public MyStockNumDto findNum(Map<String,String>body)throws EmptyResultDataAccessException{
        var rowMapper = BeanPropertyRowMapper.newInstance(MyStockNumDto.class);
        MyStockNumDto myStockNumDto=jdbcTemplate.queryForObject(
                    "SELECT stk_num\n" +
                        "from holdingstock\n" +
                        "where user_id = ? and stock_stk_cd = ?;",rowMapper,body.get("id"),body.get("cd"));
        return myStockNumDto;
    }

    //매도
    public IsSuccessDto deleteTrading(TradingDto tradingDto)throws EmptyResultDataAccessException {
        //보유하고 있는 주식 수
        int stkNum = jdbcTemplate.queryForObject(
                "select stk_num\n" +
                        "from holdingstock\n" +
                        "where user_id=? and stock_stk_cd=?", Integer.class, tradingDto.getId(), tradingDto.getCd());
        //주식 수보다 많이 판매 못함
        if (stkNum >= tradingDto.getNum()) {
            if(stkNum > tradingDto.getNum()){
                //거래내역 추가
                jdbcTemplate.update(
                            "insert into transaction_description(user_id,stock_stk_cd,stk_num,buying,selling)\n" +
                                "values(?, ?, ?, ?, ?);\n", tradingDto.getId(), tradingDto.getCd(), tradingDto.getNum(), 0, tradingDto.getPrice());
                //holdingstock주식 수 제거
                jdbcTemplate.update(
                            "update holdingstock\n" +
                                "set stk_num = stk_num-?\n" +
                                "where user_id = ? and stock_stk_cd = ? ", tradingDto.getNum(), tradingDto.getId(), tradingDto.getCd());
                //손익 업데이트
                jdbcTemplate.update(
                                "update holdingstock h ,(select s_last  from stock_quote\n" +
                                    "where STOCK_STK_CD = ? and Date(s_date) = '2022-10-28')q\n" +
                                    "set h.gain_loss = (q.s_last - h.average_price) * h.stk_num \n" +
                                    "where h.user_id = ? and h.stock_stk_cd = ? ",tradingDto.getCd(),tradingDto.getId(), tradingDto.getCd());
            }
            //보유 주식 다 팔 때
            if(stkNum == tradingDto.getNum()) {
                jdbcTemplate.update(
                            "DELETE FROM holdingstock\n" +
                                "WHERE user_id = ? and stock_stk_cd = ?", tradingDto.getId(), tradingDto.getCd());
            }
            //잔고 업데이트
            jdbcTemplate.update(
                    "update s_user\n" +
                            "set balance = balance + ?\n" +
                            "where id = ?", tradingDto.getNum() * tradingDto.getPrice(), tradingDto.getId());
            return new IsSuccessDto(true);
        }
        else {
            return new IsSuccessDto(false);
        }
    }
}
