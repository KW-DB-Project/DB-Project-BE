package com.KiHoonLee.DBProject.dto.enterprise;

import com.KiHoonLee.DBProject.table.StockQuote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeveralEnterpriseInfoDto {
    //기업정보 및 시세 정보
    private EnterpriseAndStockQuoteDto enterpriseAndStockQuoteDto;
    //기업의 주요주주 정보
    private List<MainStockHolder> mainStockHolders;

}
