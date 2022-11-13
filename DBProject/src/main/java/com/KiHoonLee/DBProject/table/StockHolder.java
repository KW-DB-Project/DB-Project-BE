package com.KiHoonLee.DBProject.table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockHolder {
    private String id;
    private String pw;
    private String uNm;
    private int age;
    private int balance;
}
