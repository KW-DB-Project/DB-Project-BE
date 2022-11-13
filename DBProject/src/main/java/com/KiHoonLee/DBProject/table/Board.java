package com.KiHoonLee.DBProject.table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Board {
    private int idx;
    private String userId;
    private String stockStkCd;
    private String title;
    private String content;
    private Date creatDate;
    private int bLike;
}
