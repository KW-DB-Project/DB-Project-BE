package com.KiHoonLee.DBProject.dto.trade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchStockDto {
    private StockPriceDto stockPriceDto;
    private List<LastPriceDto> lastPriceDto;
}
