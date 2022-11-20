package com.KiHoonLee.DBProject.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepositReceivedDto {
    private String id;
    private int balance;
}
