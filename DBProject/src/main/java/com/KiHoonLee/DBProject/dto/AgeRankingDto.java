package com.KiHoonLee.DBProject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AgeRankingDto {
    private String ageGroup;
    private String stkNm;
    private int sLast;
    private int cnt;
}
