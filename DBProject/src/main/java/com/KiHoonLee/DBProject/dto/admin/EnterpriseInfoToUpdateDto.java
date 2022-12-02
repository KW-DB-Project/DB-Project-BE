package com.KiHoonLee.DBProject.dto.admin;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnterpriseInfoToUpdateDto {
    private String stockCode;
    private String oldEntNm;
    private String newEntNm;
    private String newEntSmr;
}
