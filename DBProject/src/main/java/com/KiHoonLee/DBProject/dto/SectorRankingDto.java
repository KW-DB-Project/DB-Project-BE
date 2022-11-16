package com.KiHoonLee.DBProject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SectorRankingDto {
    private String category;
    private String stkNm;
    private int sLast;
    private int cnt;
}
