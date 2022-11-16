package com.KiHoonLee.DBProject.controller;

import com.KiHoonLee.DBProject.dto.AgeRankingDto;
import com.KiHoonLee.DBProject.dto.AmountRankingDto;
import com.KiHoonLee.DBProject.dto.SectorRankingDto;
import com.KiHoonLee.DBProject.repository.StockRepository;
import com.KiHoonLee.DBProject.table.StockNamePriceChange;
import com.KiHoonLee.DBProject.table.StockNamePriceVolume;
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

    @GetMapping("/rank1")
    public ResponseEntity<?> getAgeRaking(){
        List<AgeRankingDto> ageRankingDto = stockRepository.findAgeRaking();
        return new ResponseEntity<>(ageRankingDto, HttpStatus.OK);
    }

    @GetMapping("/rank2")
    public ResponseEntity<?> getAmountRaking(){
        List<AmountRankingDto> amountRankingDto = stockRepository.findAmountRaking();
        return new ResponseEntity<>(amountRankingDto, HttpStatus.OK);
    }

    @GetMapping("/rank3")
    public ResponseEntity<?> getSectorRaking() {
        List<SectorRankingDto> sectorRankingDto = stockRepository.findSectorRaking();
        return new ResponseEntity<>(sectorRankingDto, HttpStatus.OK);
    }

    //국내 관심 주식리스트
    @GetMapping("/stock/interest")
    public ResponseEntity<?> getStockInterestList() {
        List<StockNamePriceChange> stockNamePriceChanges = stockRepository.findAllStockInterest();
        return new ResponseEntity<>(stockNamePriceChanges, HttpStatus.OK);
    }
    //국내 등락비
    @GetMapping("/stock/fluctuation")
    public ResponseEntity<?> getStockFluctuation() {
        List<StockNamePriceChange> stockNamePriceChanges = stockRepository.findAllStockFluctuation();
        return new ResponseEntity<>(stockNamePriceChanges, HttpStatus.OK);
    }

    //국내 거래량
    @GetMapping("/stock/volume")
    public ResponseEntity<?> getStockVolume() {
        List<StockNamePriceVolume> stockNamePriceVolumes = stockRepository.findAllStockVolume();
        return new ResponseEntity<>(stockNamePriceVolumes, HttpStatus.OK);
    }
}
