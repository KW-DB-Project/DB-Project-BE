package com.KiHoonLee.DBProject.service;

import com.KiHoonLee.DBProject.dto.SoaringStockDto;
import com.KiHoonLee.DBProject.dto.TradingVolumeDto;
import com.KiHoonLee.DBProject.repository.MainRepository;
import com.KiHoonLee.DBProject.repository.UserRepository;
import com.KiHoonLee.DBProject.table.StockNamePrice;
import com.KiHoonLee.DBProject.table.StockNamePriceChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {
    @Autowired
    private MainRepository mainRepository;

    public List<SoaringStockDto> getSoaringStock() {
        List<SoaringStockDto> soaringStockDto = mainRepository.findSoaringStock();
        return soaringStockDto;
    }

    public List<TradingVolumeDto> getTradingVolume() {
        List<TradingVolumeDto> tradingVolumeDto = mainRepository.findTradingVolume();
        return tradingVolumeDto;
    }

    public List<StockNamePrice> getInterestRank() {
        List<StockNamePrice> stockNamePrices = mainRepository.findInterestRank();
        return stockNamePrices;
    }

    public List<StockNamePriceChange> getFluctuationRank() {
        List<StockNamePriceChange> stockNamePriceChanges = mainRepository.findFluctuationRank();
        return stockNamePriceChanges;
    }
}
