package com.KiHoonLee.DBProject.service;

import com.KiHoonLee.DBProject.dto.enterprise.EnterpriseAndStockQuoteDto;
import com.KiHoonLee.DBProject.dto.enterprise.MainStockHolder;
import com.KiHoonLee.DBProject.dto.enterprise.SeveralEnterpriseInfoDto;
import com.KiHoonLee.DBProject.repository.EnterpriseInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EnterpriseInfoService {
    @Autowired
    private EnterpriseInfoRepository enterpriseInfoRepository;
    public List<SeveralEnterpriseInfoDto> getEnterpriseInfos() {
        //기업의 모든 정보
        List<SeveralEnterpriseInfoDto> severalEnterpriseInfoDtos = findAllEnterpriseInfos();
        return severalEnterpriseInfoDtos;
    }

    //모든기업의 기업정보,시제정보,주요주주 정보를 찾음
    public List<SeveralEnterpriseInfoDto> findAllEnterpriseInfos() {
        List<SeveralEnterpriseInfoDto> severalEnterpriseInfoDtos = new ArrayList<>();
        //모든기업의 기업정보와 해당기업의 시세정보를 찾음
        List<EnterpriseAndStockQuoteDto> enterpriseAndStockQuoteDtos = enterpriseInfoRepository.findEnterpriseAndStockQuoteInfo();
        for (var enterprise : enterpriseAndStockQuoteDtos) {
            //각 기업에 해당하는 주요주주 정보를 찾음
            List<MainStockHolder> mainStockHolders = enterpriseInfoRepository.findMainStockHolderByStockCode(enterprise.getStockStkCd());

            severalEnterpriseInfoDtos.add(new SeveralEnterpriseInfoDto(enterprise, mainStockHolders));
        }
        return  severalEnterpriseInfoDtos;
    }
}
