package com.KiHoonLee.DBProject.table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockNamePriceChange {
    private String stkNm;
    private int sLast;
    private float sChg;
}