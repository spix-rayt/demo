package com.example.demo.dto;

public class BalanceResponseDto {

    private long balance;

    public BalanceResponseDto(long balance) {
        this.balance = balance;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
