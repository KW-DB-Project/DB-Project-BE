package com.KiHoonLee.DBProject.controller;

import com.KiHoonLee.DBProject.table.Stock;
import com.KiHoonLee.DBProject.table.StockQuote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/test1")
    public ResponseEntity<?> getRising(){
        var rowMapper = BeanPropertyRowMapper.newInstance(StockQuote.class);
        List<StockQuote> StockQuote = jdbcTemplate.query("select * from stock_quote where Date(s_date)='2022-10-28' order by s_chg desc limit 4",rowMapper);
        return new ResponseEntity<>(StockQuote, HttpStatus.OK);
    }

    @GetMapping("/test2")
    public ResponseEntity<?> getVol(){
        var rowMapper = BeanPropertyRowMapper.newInstance(StockQuote.class);
        List<StockQuote> StockQuote = jdbcTemplate.query("select * from stock_quote where Date(s_date)='2022-10-28' order by s_vol desc limit 5",rowMapper);
        return new ResponseEntity<>(StockQuote, HttpStatus.OK);
    }

}

//Date(now()); 오늘 날짜 체크 되니까
//SELECT DAYOFWEEK(NOW()); 주말 예외처리 일요일 1 토요일 7
/*  SELECT *
    FROM db.stock_quote
    where
        case
        when DAYOFWEEK(S_date)=7
        then Date(s_date)=DATE_ADD(Date(NOW()), INTERVAL -1 DAY)
        when DAYOFWEEK(S_date)=1
        then Date(s_date)=DATE_ADD(Date(NOW()), INTERVAL -2 DAY)
        else Date(s_date)=Date(now())
        end
    order by s_vol desc
    limit 5;
*/



