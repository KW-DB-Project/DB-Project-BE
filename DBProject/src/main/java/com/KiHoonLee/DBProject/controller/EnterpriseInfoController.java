package com.KiHoonLee.DBProject.controller;

import com.KiHoonLee.DBProject.dto.enterprise.SeveralEnterpriseInfoDto;
import com.KiHoonLee.DBProject.service.EnterpriseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/enterpriseInfo")
public class EnterpriseInfoController {
    @Autowired
    private EnterpriseInfoService enterpriseInfoService;
    //기업의 상세 정보를 가져옴
    @GetMapping
    public ResponseEntity<?> getEnterpriseInfo() {
        List<SeveralEnterpriseInfoDto> enterpriseInfos = enterpriseInfoService.getEnterpriseInfos();

        return new ResponseEntity<>(enterpriseInfos, HttpStatus.OK);
    }
}
