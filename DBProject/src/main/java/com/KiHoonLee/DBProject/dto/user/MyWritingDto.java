package com.KiHoonLee.DBProject.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class MyWritingDto {
    private int idx;
    private String stkNm;
    private String title;
    private Date createDate;
    private int bLike;
}
