package com.KiHoonLee.DBProject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String id;
    private String pw;
    private String userName;
    private int age;
    private int balance;

}
