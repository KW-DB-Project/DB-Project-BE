package com.KiHoonLee.DBProject.repository;

import com.KiHoonLee.DBProject.dto.KospiForGraphDto;
import com.KiHoonLee.DBProject.dto.SoaringStockDto;
import com.KiHoonLee.DBProject.dto.TradingVolumeDto;
import com.KiHoonLee.DBProject.table.StockNamePrice;
import com.KiHoonLee.DBProject.table.StockNamePriceChange;
import com.KiHoonLee.DBProject.table.StockQuote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MainRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<SoaringStockDto> findSoaringStock() {
        var rowMapper = BeanPropertyRowMapper.newInstance(SoaringStockDto.class);
        List<SoaringStockDto> soaringStockDto = jdbcTemplate.query(
                    "select s.STK_NM, q.S_LAST, q.S_CHG " +
                        "from stock s, stock_quote q " +
                        "where Date(s_date)='2022-10-28' and s.STK_CD=q.STOCK_STK_CD " +
                        "order by s_chg desc " +
                        "limit 4", rowMapper);
        return soaringStockDto;
    }

    public List<TradingVolumeDto> findTradingVolume() {
        var rowMapper = BeanPropertyRowMapper.newInstance(TradingVolumeDto.class);
        List<TradingVolumeDto> tradingVolumeDto = jdbcTemplate.query(
                    "select s.STK_NM, q.S_LAST " +
                        "from stock s, stock_quote q " +
                        "where Date(s_date)='2022-10-28' and s.STK_CD=q.STOCK_STK_CD " +
                        "order by s_vol desc " +
                        "limit 5", rowMapper);
        return tradingVolumeDto;
    }

    //홈 화면 관심순위 상위 5개를 얻음
    // WATCHLIST의 STOCK_STK_CD를 기준으로 그룹으로 묶고 개수를 센 후 큰 순서로 id와 종가출력
    public List<StockNamePrice> findInterestRank() {
        var rowMapper = BeanPropertyRowMapper.newInstance(StockNamePrice.class);
        List<StockNamePrice> stockNamePrices = jdbcTemplate.query(
                "SELECT stk.STK_NM, S.S_LAST\n" +
                "FROM WATCHLIST as W, STOCK_QUOTE as S, STOCK as stk\n" +
                "WHERE \n" +
                "\tcase\n" +
                "\twhen DAYOFWEEK('2022-10-28')=7\n" +
                "\tthen s_date=DATE_ADD(Date('2022-10-28'), INTERVAL -1 DAY)\n" +
                "\twhen DAYOFWEEK('2022-10-28')=1\n" +
                "\tthen s_date=DATE_ADD(Date('2022-10-28'), INTERVAL -2 DAY)\n" +
                "\telse Date(s_date)='2022-10-28'\n" +
                "\tend\n" +
                "\tAND W.STOCK_STK_CD = S.STOCK_STK_CD \n" +
                "    AND stk.STK_CD = W.STOCK_STK_CD\n" +
                "GROUP BY W.STOCK_STK_CD, S.S_LAST\n" +
                "ORDER BY COUNT(W.STOCK_STK_CD) DESC\n" +
                "LIMIT 5",rowMapper);
        return stockNamePrices;
    }

    //홈 화면에서 급등주 상위 2개 급락주 하위 2개를 얻음
    public List<StockNamePriceChange> findFluctuationRank() {
        var rowMapper = BeanPropertyRowMapper.newInstance(StockNamePriceChange.class);
        List<StockNamePriceChange> stockNamePriceChanges = jdbcTemplate.query("(SELECT stk.STK_NM, s.S_LAST, s.S_CHG\n" +
                "FROM STOCK_QUOTE as s, STOCK as stk\n" +
                "WHERE \n" +
                "\tcase\n" +
                "\twhen DAYOFWEEK('2022-10-28')=7\n" +
                "\tthen s.s_date=DATE_ADD(Date('2022-10-28'), INTERVAL -1 DAY)\n" +
                "\twhen DAYOFWEEK('2022-10-28')=1\n" +
                "\tthen s.s_date=DATE_ADD(Date('2022-10-28'), INTERVAL -2 DAY)\n" +
                "\telse Date(s.s_date)='2022-10-28'\n" +
                "\tend\n" +
                "\tAND s.STOCK_STK_CD=stk.STK_CD\n" +
                "ORDER BY s.S_CHG DESC\n" +
                "LIMIT 2)\n" +
                "UNION\n" +
                "(SELECT stk.STK_NM, s.S_LAST, s.S_CHG\n" +
                "FROM STOCK_QUOTE as s, STOCK as stk\n" +
                "WHERE \n" +
                "\tcase\n" +
                "\twhen DAYOFWEEK('2022-10-28')=7\n" +
                "\tthen s.s_date=DATE_ADD(Date('2022-10-28'), INTERVAL -1 DAY)\n" +
                "\twhen DAYOFWEEK('2022-10-28')=1\n" +
                "\tthen s.s_date=DATE_ADD(Date('2022-10-28'), INTERVAL -2 DAY)\n" +
                "\telse Date(s.s_date)='2022-10-28'\n" +
                "\tend\n" +
                "    AND s.STOCK_STK_CD=stk.STK_CD\n" +
                "ORDER BY s.S_CHG ASC\n" +
                "LIMIT 2)",rowMapper);
        return stockNamePriceChanges;
    }

    public List<KospiForGraphDto> findLast30Kospi() {
        var rowMapper = BeanPropertyRowMapper.newInstance(KospiForGraphDto.class);
        List<KospiForGraphDto> last30Kospi = jdbcTemplate.query(
                "SELECT S_DATE, S_LAST\n" +
                "FROM STOCK_QUOTE\n" +
                "WHERE STOCK_STK_CD='999999'\n" +
                "ORDER BY S_DATE DESC\n" +
                "LIMIT 30", rowMapper);
        return last30Kospi;
    }
}
