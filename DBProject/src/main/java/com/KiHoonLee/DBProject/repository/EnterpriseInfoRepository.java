package com.KiHoonLee.DBProject.repository;

import com.KiHoonLee.DBProject.dto.enterprise.EnterpriseAndStockQuoteDto;
import com.KiHoonLee.DBProject.dto.enterprise.MainStockHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnterpriseInfoRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    //기업의 정보 및 시세를 찾음
    public List<EnterpriseAndStockQuoteDto> findEnterpriseAndStockQuoteInfo() {
        var rowMapper = BeanPropertyRowMapper.newInstance(EnterpriseAndStockQuoteDto.class);
        List<EnterpriseAndStockQuoteDto> enterpriseAndStockQuoteDtos = jdbcTemplate.query(
                "SELECT E.ENT_NM, E.STOCK_STK_CD, E.LS, E.ENT_SMRY, E.CATEGORY, S.S_LAST, S.S_OPEN, S.S_HIGH, S.S_LOW, S.S_VOL, S.S_CHG\n" +
                "FROM ENTERPRISE_INFO E, STOCK_QUOTE S\n" +
                "WHERE E.STOCK_STK_CD=S.STOCK_STK_CD AND Date(S.S_DATE)='2022-10-28'",
                rowMapper
        );
        return enterpriseAndStockQuoteDtos;
    }
    //해당 주식의 주요주주정보를 찾음
    public List<MainStockHolder> findMainStockHolderByStockCode(String stockStkCd) {
        var rowMapper = BeanPropertyRowMapper.newInstance(MainStockHolder.class);
        List<MainStockHolder> mainStockHolders = jdbcTemplate.query("SELECT M_STOCKHOLDER, STOCK_NUM, STAKE\n" +
                "FROM STOCKHOLDER\n" +
                "WHERE STOCK_STK_CD=?",
                rowMapper, stockStkCd);

        return mainStockHolders;
    }
}
