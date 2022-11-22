package com.KiHoonLee.DBProject.controller;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.trade.BalanceDto;
import com.KiHoonLee.DBProject.dto.trade.TradingDto;
import com.KiHoonLee.DBProject.dto.trade.MyStockNumDto;
import com.KiHoonLee.DBProject.dto.trade.SearchStockDto;
import com.KiHoonLee.DBProject.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/trade")
public class TradingController {
    @Autowired
    private TradingService tradingService;

    @GetMapping("/search") //검색해서 기업 정보
    public ResponseEntity<?> getSearchStock(@RequestParam("name") String name){
        SearchStockDto searchStockDto = tradingService.getSearchStock(name);
        return new ResponseEntity<>(searchStockDto, HttpStatus.OK);
    }

    @PostMapping("/interest") //관심 기업 등록
    public ResponseEntity<?> insertInterestStock(@RequestBody Map<String,String> body){
        IsSuccessDto isSuccessDto = tradingService.insertInterestStock(body);
        return new ResponseEntity<>(isSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/balance")//보유 잔고
    ResponseEntity<?> getBalance(@RequestBody Map<String,String> body) {
        BalanceDto balanceDto = tradingService.getBalance(body);
        return new ResponseEntity<>(balanceDto, HttpStatus.OK);
    }

    @PostMapping("/buy")//매수 버튼 클릭 시 주문
    ResponseEntity<?> buyStock(@RequestBody TradingDto tradingDto) {
        IsSuccessDto isSuccessDto = tradingService.buyStock(tradingDto);
        return new ResponseEntity<>(isSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/num")//해당 주식 보유 수, 평단가
    ResponseEntity<?> getNum(@RequestBody Map<String,String> body) {
        MyStockNumDto myStockNumDto = tradingService.getNum(body);
        return new ResponseEntity<>(myStockNumDto, HttpStatus.OK);
    }

    @PostMapping("/sell") //매도 버튼 클릭시 주문
    ResponseEntity<?> sellStock(@RequestBody TradingDto tradingDto) {
        IsSuccessDto isSuccessDto = tradingService.sellStock(tradingDto);
        return new ResponseEntity<>(isSuccessDto, HttpStatus.OK);
    }
}
