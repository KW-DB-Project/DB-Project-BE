package com.KiHoonLee.DBProject.controller;

import com.KiHoonLee.DBProject.dto.AgeRankingDto;
import com.KiHoonLee.DBProject.dto.AmountRankingDto;
import com.KiHoonLee.DBProject.dto.SectorRankingDto;
import com.KiHoonLee.DBProject.dto.TradingVolumeDto;
import com.KiHoonLee.DBProject.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockController {
    @Autowired
    StockRepository stockRepository;

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
    public ResponseEntity<?> getSectorRaking(){
        List<SectorRankingDto> sectorRankingDto = stockRepository.findSectorRaking();
        return new ResponseEntity<>(sectorRankingDto, HttpStatus.OK);
    }
}
