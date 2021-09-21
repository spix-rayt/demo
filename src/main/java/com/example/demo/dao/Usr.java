package com.example.demo.dao;

public class Usr {

    private String login;
    private String token;
    private Long balance;

    public Usr() {
    }

    public Usr(String login, String token, Long balance) {
        this.login = login;
        this.token = token;
        this.balance = balance;
    }

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
