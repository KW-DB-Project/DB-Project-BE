package com.KiHoonLee.DBProject.service;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.trade.*;
import com.KiHoonLee.DBProject.repository.TradingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TradingService {
    @Autowired
    private TradingRepository tradingRepository;

    //검색한 기업 정보 긁어옴
    public SearchStockDto getSearchStock(String name){
        try {
            StockPriceDto stockPriceDto= tradingRepository.findStockPrice(name);
            List<LastPriceDto>lastPriceDto=tradingRepository.findLastPrice(name);
            SearchStockDto searchStockDto = new SearchStockDto(stockPriceDto,lastPriceDto);
            return searchStockDto;
        }
        catch (EmptyResultDataAccessException e){
            SearchStockDto searchStockDto = new SearchStockDto(null ,null);
            return searchStockDto;
        }
    }
    public IsSuccessDto getInterestStock(Map<String,String> body){
        IsSuccessDto isSuccessDto= tradingRepository.findInterestStock(body);
        return isSuccessDto;
    }

    //잔고 조회
    public BalanceDto getBalance(Map<String,String> body){
        try{
            BalanceDto balanceDto= tradingRepository.findBalance(body);
            return balanceDto;
        }
        catch (EmptyResultDataAccessException e){
            return new BalanceDto(null,0);
        }
    }

    //관심 종목
    public IsSuccessDto insertInterestStock(Map<String,String>body){
        try{
            IsSuccessDto isSuccessDto= tradingRepository.saveInterestStock(body);
            return isSuccessDto;
        }
        catch (EmptyResultDataAccessException e){
            return new IsSuccessDto(false);
        }
    }

    //주식 매수
    public IsSuccessDto buyStock(TradingDto tradingDto){
        try{
            IsSuccessDto isSuccessDto= tradingRepository.insertBuying(tradingDto);
            return isSuccessDto;
        }
        catch (EmptyResultDataAccessException e){
            return new IsSuccessDto(false);
        }
    }

    //가지고 있는 주식 수
    public MyStockNumDto getNum(Map<String,String>body){
        try{
            MyStockNumDto myStockNumDto= tradingRepository.findNum(body);
            return myStockNumDto;
        }
        catch (EmptyResultDataAccessException e){
            return new MyStockNumDto(0);
        }
    }

    //주식 매도
    public IsSuccessDto sellStock(TradingDto tradingDto){
        try{
            IsSuccessDto isSuccessDto= tradingRepository.deleteTrading(tradingDto);
            return isSuccessDto;
        }
        catch (EmptyResultDataAccessException e){
            return new IsSuccessDto(false);
        }
    }
}
