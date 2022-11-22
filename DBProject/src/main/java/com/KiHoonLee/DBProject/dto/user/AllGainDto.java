package com.KiHoonLee.DBProject.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllGainDto {
    private float rateOfReturn;
    private int appraisalAmount;
    private List<MyStockDto> myStockDto;
}
