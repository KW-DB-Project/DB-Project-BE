package com.KiHoonLee.DBProject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SoaringStockDto {
    private String stkNm ;
    private int sLast;
    private int sVol;
}
