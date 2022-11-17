package com.KiHoonLee.DBProject.service;

import com.KiHoonLee.DBProject.repository.TradingRepository;
import com.KiHoonLee.DBProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradingService {
    @Autowired
    private TradingRepository tradingRepository;
}
