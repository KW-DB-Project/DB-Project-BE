package com.KiHoonLee.DBProject.table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostLikeInfo {
    private int postIdx;
    private String userId;
}
