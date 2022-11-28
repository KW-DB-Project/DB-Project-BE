package com.KiHoonLee.DBProject.dto.community;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDto {
    private String  id;
    private String stockName;
    private String title;
    private String content;
}
