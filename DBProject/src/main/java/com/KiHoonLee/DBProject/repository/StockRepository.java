package com.KiHoonLee.DBProject.repository;

import com.KiHoonLee.DBProject.dto.AgeRankingDto;
import com.KiHoonLee.DBProject.dto.AmountRankingDto;
import com.KiHoonLee.DBProject.dto.SectorRankingDto;
import com.KiHoonLee.DBProject.table.StockNamePriceChange;
import com.KiHoonLee.DBProject.table.StockNamePriceVolume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AgeRankingDto> findAgeRaking() {
        var rowMapper = BeanPropertyRowMapper.newInstance(AgeRankingDto.class);
        List<AgeRankingDto> ageRankingDto = jdbcTemplate.query(
                "select  case\n" +
                    "      when u.age between 10 and 19 then '10대'\n" +
                    "      when u.age between 20 and 29 then '20대'\n" +
                    "      when u.age between 30 and 39 then '30대'\n" +
                    "      when u.age between 40 and 49 then '40대'\n" +
                    "      else '50대 이상'\n" +
                    "      end as age_group,s.STK_NM, q.S_LAST, count(w.STOCK_STK_CD) as cnt\n" +
                    "from s_user u, watchlist w, stock_quote q , stock s\n" +
                    "where u.id = w.s_user_id and q.STOCK_STK_CD = s.STK_CD and s.STK_CD = w.STOCK_STK_CD and Date(s_date)='2022-10-28'\n" +
                    "group by w.stock_stk_cd, age_group,s.STK_NM, q.S_LAST\n" +
                    "order by age_group, count(w.STOCK_STK_CD) desc",rowMapper);
        return ageRankingDto;
    }

    public List<AmountRankingDto> findAmountRaking() { //
        var rowMapper = BeanPropertyRowMapper.newInstance(AmountRankingDto.class);
        List<AmountRankingDto> amountRankingDto = jdbcTemplate.query(
                "select  case\n" +
                    "      when q.S_LAST between 10000 and 49999 then '만원'\n" +
                    "      when q.S_LAST between 50000 and 99999 then '오만원'\n" +
                    "      when q.S_LAST between 100000 and 499999 then '십만원'\n" +
                    "      when q.S_LAST between 500000 and 1000000 then '오십만원'\n" +
                    "      end as money_group, s.STK_NM, q.S_LAST, count(w.STOCK_STK_CD) as cnt\n" +
                    "from s_user u, watchlist w, stock_quote q , stock s\n" +
                    "where u.id = w.s_user_id and q.STOCK_STK_CD = s.STK_CD and s.STK_CD = w.STOCK_STK_CD and Date(s_date)='2022-10-28'\n" +
                    "group by w.stock_stk_cd, money_group, s.STK_NM, q.S_LAST\n" +
                    "order by money_group, count(w.STOCK_STK_CD) desc",rowMapper);
        return amountRankingDto;
    }

    public List<SectorRankingDto> findSectorRaking() {
        var rowMapper = BeanPropertyRowMapper.newInstance(SectorRankingDto.class);
        List<SectorRankingDto> sectorRankingDto = jdbcTemplate.query(
                "select  e.CATEGORY, s.STK_NM, q.S_LAST, count(w.STOCK_STK_CD) as cnt\n" +
                    "from enterprise_info e, s_user u, watchlist w, stock_quote q , stock s\n" +
                    "where u.id = w.s_user_id and q.STOCK_STK_CD = s.STK_CD and s.STK_CD = w.STOCK_STK_CD and s.STK_CD = e.STOCK_STK_CD \n" +
                    "and Date(s_date)='2022-10-28'\n" +
                    "group by e.CATEGORY, w.stock_stk_cd, s.STK_NM, q.S_LAST\n" +
                    "order by e.CATEGORY asc, count(w.STOCK_STK_CD) desc\n",rowMapper);
        return sectorRankingDto;
    }

    //국내탭에서 관심종목 리스트를 얻음
    //관심종목 중 관심순위 기준
    public List<StockNamePriceChange> findAllStockInterest() {
        var rowMapper = BeanPropertyRowMapper.newInstance(StockNamePriceChange.class);
        List<StockNamePriceChange> stockNamePriceChanges = jdbcTemplate.query(
                "SELECT stk.STK_NM, S.S_LAST, S.S_CHG\n" +
                "FROM WATCHLIST as W, STOCK_QUOTE as S, STOCK as stk\n" +
                "WHERE Date(S_DATE) = '2022-10-28' AND W.STOCK_STK_CD = S.STOCK_STK_CD AND stk.STK_CD = W.STOCK_STK_CD AND stk.STK_CD NOT IN ('999999')\n" +
                "GROUP BY W.STOCK_STK_CD, S.S_LAST, S.S_CHG\n" +
                "ORDER BY COUNT(W.STOCK_STK_CD) DESC",rowMapper);
        return stockNamePriceChanges;
    }

    //국내 등락비 기준 리스트
    public List<StockNamePriceChange> findAllStockFluctuation() {
        var rowMapper = BeanPropertyRowMapper.newInstance(StockNamePriceChange.class);
        List<StockNamePriceChange> stockNamePriceChanges = jdbcTemplate.query(
                    "SELECT stk.STK_NM, s.S_LAST, s.S_CHG\n" +
                        "FROM STOCK_QUOTE as s, STOCK as stk\n" +
                        "WHERE Date(s.S_DATE)='2022-10-28' AND s.STOCK_STK_CD=stk.STK_CD\n" +
                        "ORDER BY s.S_CHG DESC",rowMapper);
        return stockNamePriceChanges;
    }

    //국내 거래량 기준 리스트
    public List<StockNamePriceVolume> findAllStockVolume() {
        var rowMapper = BeanPropertyRowMapper.newInstance(StockNamePriceVolume.class);
        List<StockNamePriceVolume> stockNamePriceVolumes = jdbcTemplate.query(
                    "SELECT stk.STK_NM, s.S_LAST, s.S_VOL\n" +
                        "FROM STOCK_QUOTE as s, STOCK as stk\n" +
                        "WHERE Date(s.S_DATE)='2022-10-28' AND s.STOCK_STK_CD=stk.STK_CD\n" +
                        "ORDER BY s.S_VOL DESC",rowMapper);
        return stockNamePriceVolumes;
    }
}
/*
select  case
		when u.age between 10 and 19 then '10대'
        when u.age between 20 and 29 then '20대'
        when u.age between 30 and 39 then '30대'
        when u.age between 40 and 49 then '40대'
        else '50대 이상'
        end as age_group,
		s.STK_NM, q.S_LAST, count(w.STOCK_STK_CD) as c
from s_user u, watchlist w, stock_quote q , stock s
where u.id = w.s_user_id and q.STOCK_STK_CD = s.STK_CD and s.STK_CD = w.STOCK_STK_CD and Date(s_date)='2022-10-28'
group by w.stock_stk_cd, u.AGE div 10
order by age_group, count(w.STOCK_STK_CD) desc
원래라면 저기 10월 28일이 아닌 앞에서 작성한 주말 예외처리 문 사용한 후
now()로 바꿔야 한다.
 */