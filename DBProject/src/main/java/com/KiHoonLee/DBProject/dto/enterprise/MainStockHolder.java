package com.KiHoonLee.DBProject.dto.enterprise;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MainStockHolder {
    private String mStockholder;
    private int stockNum;
    private float stake;
}
