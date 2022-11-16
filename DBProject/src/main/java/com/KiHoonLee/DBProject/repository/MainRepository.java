package com.KiHoonLee.DBProject.repository;

import com.KiHoonLee.DBProject.dto.SoaringStockDto;
import com.KiHoonLee.DBProject.dto.TradingVolumeDto;
import com.KiHoonLee.DBProject.table.Stock;
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
    public List<SoaringStockDto> findSoaringStock() {
        var rowMapper = BeanPropertyRowMapper.newInstance(SoaringStockDto.class);
        List<SoaringStockDto> soaringStockDto = jdbcTemplate.query("select s.STK_NM, q.S_LAST, q.S_CHG from stock s, stock_quote q where Date(s_date)='2022-10-28' and s.STK_CD=q.STOCK_STK_CD order by s_chg desc limit 4",rowMapper);
        return soaringStockDto;
    }

    public List<TradingVolumeDto> findTradingVolume() {
        var rowMapper = BeanPropertyRowMapper.newInstance(TradingVolumeDto.class);
        List<TradingVolumeDto> tradingVolumeDto = jdbcTemplate.query("select s.STK_NM, q.S_LAST from stock s, stock_quote q where Date(s_date)='2022-10-28' and s.STK_CD=q.STOCK_STK_CD order by s_vol desc limit 5",rowMapper);
        return tradingVolumeDto;
    }
}
