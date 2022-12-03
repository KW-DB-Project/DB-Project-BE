package com.KiHoonLee.DBProject.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseInfoDto {
    private String entNm;
    private String entSmry;
    private String stockStkCd;
    private int sLast;
}
