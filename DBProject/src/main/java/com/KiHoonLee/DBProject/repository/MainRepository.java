package com.KiHoonLee.DBProject.repository;

import com.KiHoonLee.DBProject.table.Stock;
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

    //WATCHLIST의 STOCK_STK_CD를 기준으로 그룹으로 묶고 개수를 센 후 큰 순서로 id와 종가출력
    public List<StockQuote> findInterestRank() {
        var rowMapper = BeanPropertyRowMapper.newInstance(StockQuote.class);
        List<StockQuote> stockQuotes = jdbcTemplate.query(
                "SELECT W.STOCK_STK_CD, S.S_LAST\n" +
                "FROM WATCHLIST as W, STOCK_QUOTE as S\n" +
                "WHERE Date(S_DATE) = '2022-10-28' AND W.STOCK_STK_CD = S.STOCK_STK_CD\n" +
                "GROUP BY W.STOCK_STK_CD, S.S_LAST\n" +
                "ORDER BY COUNT(W.STOCK_STK_CD) ASC\n" +
                "LIMIT 5"
                ,rowMapper);
        return stockQuotes;
    }
}
