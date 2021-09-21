package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9~_-]{1,20}$")
    private String login;

    @Size(min = 10, max = 60)
    private String token;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Min(0)
    private Long balance = 0L;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
