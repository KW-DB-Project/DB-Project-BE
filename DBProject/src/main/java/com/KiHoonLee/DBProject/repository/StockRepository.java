package com.KiHoonLee.DBProject.repository;

import com.KiHoonLee.DBProject.table.StockNamePriceChange;
import com.KiHoonLee.DBProject.table.StockNamePriceVolume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //국내탭에서 관심종목 리스트를 얻음
    //관심종목 중 등락비 기준
    public List<StockNamePriceChange> findAllStockInterest() {
        var rowMapper = BeanPropertyRowMapper.newInstance(StockNamePriceChange.class);
        List<StockNamePriceChange> stockNamePriceChanges = jdbcTemplate.query(
                "SELECT stk.STK_NM, S.S_LAST, S.S_CHG\n" +
                "FROM WATCHLIST as W, STOCK_QUOTE as S, STOCK as stk\n" +
                "WHERE Date(S_DATE) = '2022-10-28' AND W.STOCK_STK_CD = stk.STK_CD AND stk.STK_CD = S.STOCK_STK_CD\n" +
                "GROUP BY W.STOCK_STK_CD, S.S_LAST, S.S_CHG\n" +
                "ORDER BY S.S_CHG DESC"
                ,rowMapper);
        return stockNamePriceChanges;
    }

    //국내 등락비 기준 리스트
    public List<StockNamePriceChange> findAllStockFluctuation() {
        var rowMapper = BeanPropertyRowMapper.newInstance(StockNamePriceChange.class);
        List<StockNamePriceChange> stockNamePriceChanges = jdbcTemplate.query(
                "SELECT stk.STK_NM, s.S_LAST, s.S_CHG\n" +
                        "FROM STOCK_QUOTE as s, STOCK as stk\n" +
                        "WHERE Date(s.S_DATE)='2022-10-28' AND s.STOCK_STK_CD=stk.STK_CD\n" +
                        "ORDER BY s.S_CHG DESC"
                ,rowMapper);
        return stockNamePriceChanges;
    }

    //국내 거래량 기준 리스트
    public List<StockNamePriceVolume> findAllStockVolume() {
        var rowMapper = BeanPropertyRowMapper.newInstance(StockNamePriceVolume.class);
        List<StockNamePriceVolume> stockNamePriceVolumes = jdbcTemplate.query(
                "SELECT stk.STK_NM, s.S_LAST, s.S_VOL\n" +
                        "FROM STOCK_QUOTE as s, STOCK as stk\n" +
                        "WHERE Date(s.S_DATE)='2022-10-28' AND s.STOCK_STK_CD=stk.STK_CD\n" +
                        "ORDER BY s.S_VOL DESC"
                ,rowMapper);
        return stockNamePriceVolumes;
    }
}
