package com.KiHoonLee.DBProject.repository;

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
}
