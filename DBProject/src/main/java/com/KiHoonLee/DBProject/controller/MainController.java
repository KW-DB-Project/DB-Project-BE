package com.KiHoonLee.DBProject.controller;

import com.KiHoonLee.DBProject.table.Stock;
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

    @GetMapping("/test")
    public ResponseEntity<?> getStock(){
        var rowMapper = BeanPropertyRowMapper.newInstance(Stock.class);
        List<Stock> stock = jdbcTemplate.query("select STK_CD from stock",rowMapper);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }


}
