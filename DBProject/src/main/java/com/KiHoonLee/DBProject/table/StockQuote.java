package com.KiHoonLee.DBProject.table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class StockQuote {
    private String stockStkCd;
    private Date sDate;
    private int sLast;
    private int sOpen;
    private int sHigh;
    private int sLow;
    private int sVol;
    private int sChg;
}