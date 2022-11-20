package com.KiHoonLee.DBProject.table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    private int idx;
    private String userId;
    private String stockStkCd;
    private String title;
    private String content;
    private Date createDate;
    private int bLike;
}
