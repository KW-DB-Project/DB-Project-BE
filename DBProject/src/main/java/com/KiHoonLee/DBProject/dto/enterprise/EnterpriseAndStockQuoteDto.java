package com.KiHoonLee.DBProject.dto.enterprise;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseAndStockQuoteDto {
        private String entNm;
        private String stockStkCd;
        private int ls;
        private String entSmry;
        private String category;
        private int sLast;
        private int sOpen;
        private int sHigh;
        private int sLow;
        private int sVol;
        private float sChg;
}
