package com.KiHoonLee.DBProject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IsSuccessDto {
    private Boolean isTrue;

    public IsSuccessDto(Boolean isTrue) {
        this.isTrue = isTrue;
    }
}
