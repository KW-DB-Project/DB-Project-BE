package com.KiHoonLee.DBProject.repository;

import com.KiHoonLee.DBProject.dto.AgeRankingDto;
import com.KiHoonLee.DBProject.dto.AmountRankingDto;
import com.KiHoonLee.DBProject.dto.SectorRankingDto;
import com.KiHoonLee.DBProject.dto.SoaringStockDto;
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
        List<AgeRankingDto> ageRankingDto = jdbcTemplate.query("select  case\n" +
                "\t\twhen u.age between 10 and 19 then '10대'\n" +
                "        when u.age between 20 and 29 then '20대'\n" +
                "        when u.age between 30 and 39 then '30대'\n" +
                "        when u.age between 40 and 49 then '40대'\n" +
                "        else '50대 이상'\n" +
                "        end as age_group,\n" +
                "\t\ts.STK_NM, q.S_LAST, count(w.STOCK_STK_CD) as cnt\n" +
                "from s_user u, watchlist w, stock_quote q , stock s\n" +
                "where u.id = w.s_user_id and q.STOCK_STK_CD = s.STK_CD and s.STK_CD = w.STOCK_STK_CD and Date(s_date)='2022-10-28'\n" +
                "group by w.stock_stk_cd, u.AGE div 10\n" +
                "order by age_group, count(w.STOCK_STK_CD) desc\n" +
                "\n",rowMapper);
        return ageRankingDto;
    }

    public List<AmountRankingDto> findAmountRaking() {
        var rowMapper = BeanPropertyRowMapper.newInstance(AmountRankingDto.class);
        List<AmountRankingDto> amountRankingDto = jdbcTemplate.query("select  case\n" +
                "\t\twhen q.S_LAST between 10000 and 49999 then '만원대'\n" +
                "        when q.S_LAST between 50000 and 99999 then '오만원대'\n" +
                "        when q.S_LAST between 100000 and 499999 then '십만원대'\n" +
                "        when q.S_LAST between 500000 and 1000000 then '오십만원대'\n" +
                "        end as money_group,\n" +
                "\t\ts.STK_NM, q.S_LAST, count(w.STOCK_STK_CD) as cnt\n" +
                "from s_user u, watchlist w, stock_quote q , stock s\n" +
                "where u.id = w.s_user_id and q.STOCK_STK_CD = s.STK_CD and s.STK_CD = w.STOCK_STK_CD and Date(s_date)='2022-10-28'\n" +
                "group by w.stock_stk_cd, case\n" +
                "\t\twhen q.S_LAST between 10000 and 49999 then '만원대'\n" +
                "        when q.S_LAST between 50000 and 99999 then '오만원대'\n" +
                "        when q.S_LAST between 100000 and 499999 then '십만원대'\n" +
                "        when q.S_LAST between 500000 and 1000000 then '오십만원대'\n" +
                "        end \n" +
                "order by money_group, count(w.STOCK_STK_CD) desc\n" +
                "\n",rowMapper);
        return amountRankingDto;
    }

    public List<SectorRankingDto> findSectorRaking() {
        var rowMapper = BeanPropertyRowMapper.newInstance(SectorRankingDto.class);
        List<SectorRankingDto> sectorRankingDto = jdbcTemplate.query("select  e.CATEGORY, s.STK_NM, q.S_LAST, count(w.STOCK_STK_CD) as cnt\n" +
                "from enterprise_info e, s_user u, watchlist w, stock_quote q , stock s\n" +
                "where u.id = w.s_user_id and q.STOCK_STK_CD = s.STK_CD and s.STK_CD = w.STOCK_STK_CD and s.STK_CD = e.STOCK_STK_CD \n" +
                "and Date(s_date)='2022-10-28'\n" +
                "group by e.CATEGORY, w.stock_stk_cd\n" +
                "order by e.CATEGORY asc, count(w.STOCK_STK_CD) desc\n" +
                "\n" ,rowMapper);
        return sectorRankingDto;
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