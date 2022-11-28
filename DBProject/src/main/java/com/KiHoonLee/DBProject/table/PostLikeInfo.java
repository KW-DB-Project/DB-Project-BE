package com.KiHoonLee.DBProject.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeInfo {
    private int postIdx;
    private String userId;
}
