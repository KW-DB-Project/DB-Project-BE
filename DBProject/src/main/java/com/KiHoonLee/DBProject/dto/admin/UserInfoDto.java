package com.KiHoonLee.DBProject.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//유저 정보
public class UserInfoDto {
    private String id;
    private String uNm;
    private int age;
}
