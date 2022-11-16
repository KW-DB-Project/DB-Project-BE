package com.KiHoonLee.DBProject.table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockNamePriceVolume {
    private String stkNm;
    private int sLast;
    private int sVol;
}
