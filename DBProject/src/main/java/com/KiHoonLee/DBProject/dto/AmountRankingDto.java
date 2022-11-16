package com.KiHoonLee.DBProject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AmountRankingDto {
    private String moneyGroup;
    private String stkNm;
    private int sLast;
    private int cnt;
}
