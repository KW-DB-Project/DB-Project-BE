package com.KiHoonLee.DBProject.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyStockDto{
    private String userId;
    private String stockStkCd;
    private int averagePrice;//평단가
    private int stkNum;
    private int gainLoss; //손익
}
