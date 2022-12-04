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
                "SELECT s.s_date, E.ENT_NM, E.STOCK_STK_CD, E.LS, E.ENT_SMRY, E.CATEGORY, S.S_LAST, S.S_OPEN, S.S_HIGH, S.S_LOW, S.S_VOL, S.S_CHG\n" +
                        "FROM ENTERPRISE_INFO E, STOCK_QUOTE S\n" +
                        "WHERE \n" +
                        "\tcase\n" +
                        "\twhen DAYOFWEEK('2022-10-28')=7\n" +
                        "\tthen s_date=DATE_ADD(Date('2022-10-28'), INTERVAL -1 DAY)\n" +
                        "\twhen DAYOFWEEK('2022-10-28')=1\n" +
                        "\tthen s_date=DATE_ADD(Date('2022-10-28'), INTERVAL -2 DAY)\n" +
                        "\telse Date(s_date)='2022-10-28'\n" +
                        "\tend\n" +
                        "AND E.STOCK_STK_CD=S.STOCK_STK_CD ",
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
