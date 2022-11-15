package com.KiHoonLee.DBProject.controller;

import com.KiHoonLee.DBProject.repository.StockRepository;
import com.KiHoonLee.DBProject.table.StockNamePriceChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockController {
    @Autowired
    private StockRepository stockRepository;

    //국내 관심 주식리스트
    @GetMapping("/stock/interest")
    public ResponseEntity<?> getStockInterestList() {
        List<StockNamePriceChange> stockNamePriceChanges = stockRepository.findAllStockInterest();
        return new ResponseEntity<>(stockNamePriceChanges, HttpStatus.OK);
    }
}
