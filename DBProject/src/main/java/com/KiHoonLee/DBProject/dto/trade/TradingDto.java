package com.KiHoonLee.DBProject.dto.trade;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TradingDto {
    private String id;
    private String cd;
    private int price;
    private int num;
}
