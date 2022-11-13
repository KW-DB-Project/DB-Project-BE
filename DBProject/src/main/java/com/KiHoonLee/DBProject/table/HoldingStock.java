package com.KiHoonLee.DBProject.table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HoldingStock {
    private String userId;
    private String stockStkCd;
    private int stkNum;
}