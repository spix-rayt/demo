package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Controller
public class Dao {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public boolean isUserExists(String login) {
        Map<String, String> params = Map.of("login", login);
        Boolean result = jdbc.queryForObject("SELECT EXISTS (SELECT * FROM usr WHERE login=:login)", params, Boolean.class);
        return result != null && result;
    }

    public Usr getUserByLoginOrNull(String login) {
        Map<String, String> params = Map.of("login", login);
        List<Usr> result = jdbc.query("SELECT * FROM usr WHERE login=:login", params, new UsrRowMapper());
        if(result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    public void addUser(Usr user) {
        Map<String, ? extends Serializable> params = Map.of(
                "login", user.getLogin(),
                "token", user.getToken(),
                "balance", user.getBalance()
        );
        jdbc.update("INSERT INTO usr (login, token, balance) VALUES (:login, :token, :balance)", params);
    }
}
