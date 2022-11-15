package com.KiHoonLee.DBProject.repository;

import com.KiHoonLee.DBProject.table.Stock;
import com.KiHoonLee.DBProject.table.StockNamePrice;
import com.KiHoonLee.DBProject.table.StockNamePriceChange;
import com.KiHoonLee.DBProject.table.StockQuote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MainRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Stock> findAllStock() {
        var rowMapper = BeanPropertyRowMapper.newInstance(Stock.class);
        List<Stock> stock = jdbcTemplate.query("select STK_CD from stock",rowMapper);
        return stock;
    }

    //홈 화면 관심순위 상위 5개를 얻음
    // WATCHLIST의 STOCK_STK_CD를 기준으로 그룹으로 묶고 개수를 센 후 큰 순서로 id와 종가출력
    public List<StockNamePrice> findInterestRank() {
        var rowMapper = BeanPropertyRowMapper.newInstance(StockNamePrice.class);
        List<StockNamePrice> stockNamePrices = jdbcTemplate.query(
            "SELECT stk.STK_NM, S.S_LAST\n" +
                "FROM WATCHLIST as W, STOCK_QUOTE as S, STOCK as stk\n" +
                "WHERE Date(S_DATE) = '2022-10-28' AND W.STOCK_STK_CD = S.STOCK_STK_CD AND stk.STK_CD = W.STOCK_STK_CD\n" +
                "GROUP BY W.STOCK_STK_CD, S.S_LAST\n" +
                "ORDER BY COUNT(W.STOCK_STK_CD) DESC\n" +
                "LIMIT 5"
                ,rowMapper);
        return stockNamePrices;
    }

    //홈 화면에서 급등주 상위 2개 급락주 하위 2개를 얻음
    public List<StockNamePriceChange> findFluctuationRank() {
        var rowMapper = BeanPropertyRowMapper.newInstance(StockNamePriceChange.class);
        List<StockNamePriceChange> stockNamePriceChanges = jdbcTemplate.query(
            "(SELECT stk.STK_NM, s.S_LAST, s.S_CHG\n" +
                "FROM STOCK_QUOTE as s, STOCK as stk\n" +
                "WHERE Date(s.S_DATE)='2022-10-28' AND s.STOCK_STK_CD=stk.STK_CD\n" +
                "ORDER BY s.S_CHG DESC\n" +
                "LIMIT 2)\n" +
                "UNION\n" +
                "(SELECT stk.STK_NM, s.S_LAST, s.S_CHG\n" +
                "FROM STOCK_QUOTE as s, STOCK as stk\n" +
                "WHERE Date(s.S_DATE)='2022-10-28' AND s.STOCK_STK_CD=stk.STK_CD\n" +
                "ORDER BY s.S_CHG ASC\n" +
                "LIMIT 2)"
                ,rowMapper);
        return stockNamePriceChanges;
    }
}
