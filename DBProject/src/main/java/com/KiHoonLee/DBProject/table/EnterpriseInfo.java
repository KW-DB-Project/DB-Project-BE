package com.KiHoonLee.DBProject.table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnterpriseInfo {
    private String entNm;
    private String stockStkCd;
    private int ls;
    private String entSmry;
    private String category;
}
