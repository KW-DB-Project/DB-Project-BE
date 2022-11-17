package com.KiHoonLee.DBProject.service;

import com.KiHoonLee.DBProject.dto.AgeRankingDto;
import com.KiHoonLee.DBProject.dto.AmountRankingDto;
import com.KiHoonLee.DBProject.dto.SectorRankingDto;
import com.KiHoonLee.DBProject.repository.StockRepository;
import com.KiHoonLee.DBProject.repository.UserRepository;
import com.KiHoonLee.DBProject.table.StockNamePriceChange;
import com.KiHoonLee.DBProject.table.StockNamePriceVolume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public List<AgeRankingDto> getAgeRaking() {
        List<AgeRankingDto> ageRankingDto = stockRepository.findAgeRaking();
        return ageRankingDto;
    }

    public List<AmountRankingDto> getAmountRaking() {
        List<AmountRankingDto> amountRankingDto = stockRepository.findAmountRaking();
        return amountRankingDto;
    }

    public List<SectorRankingDto> getSectorRaking() {
        List<SectorRankingDto> sectorRankingDto = stockRepository.findSectorRaking();
        return sectorRankingDto;
    }

    public List<StockNamePriceChange> getAllStockInterest() {
        List<StockNamePriceChange> stockNamePriceChanges = stockRepository.findAllStockInterest();
        return stockNamePriceChanges;
    }

    public List<StockNamePriceChange> getAllStockFluctuation() {
        List<StockNamePriceChange> stockNamePriceChanges = stockRepository.findAllStockFluctuation();
        return stockNamePriceChanges;
    }

    public List<StockNamePriceVolume> getAllStockVolume() {
        List<StockNamePriceVolume> stockNamePriceVolumes = stockRepository.findAllStockVolume();
        return stockNamePriceVolumes;
    }
}
