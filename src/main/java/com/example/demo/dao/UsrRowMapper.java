package com.example.demo.dao;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsrRowMapper implements RowMapper<Usr> {
    @Override
    public Usr mapRow(ResultSet rs, int rowNum) throws SQLException {
        Usr usr = new Usr();
        usr.setLogin(rs.getString("login"));
        usr.setToken(rs.getString("token"));
        usr.setBalance(rs.getLong("balance"));
        return usr;
    }
}
