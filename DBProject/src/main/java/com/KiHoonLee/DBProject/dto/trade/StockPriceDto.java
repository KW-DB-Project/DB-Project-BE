package com.KiHoonLee.DBProject.dto.trade;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockPriceDto {
    private String stkCd;
    private int sLast;
    private int sOpen;
    private int sHigh;
    private int sLow;
    private int sVol;
    private float sChg;
}
