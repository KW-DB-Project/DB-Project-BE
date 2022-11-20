package com.KiHoonLee.DBProject.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyStockDto{
    private String userId;
    private String stockStkCd;
    private int stkNum;
    private int buying;
    private int selling;
    private int rateReturn; //수익률
    private int gainLoss; //평가손익
}
