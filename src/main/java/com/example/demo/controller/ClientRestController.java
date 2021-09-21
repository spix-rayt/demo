package com.example.demo.controller;

import com.example.demo.dao.Dao;
import com.example.demo.dao.Usr;
import com.example.demo.dto.BalanceResponseDto;
import com.example.demo.dto.RegisterRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class ClientRestController {

    @Autowired
    private Dao dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(path = "/register", method = { RequestMethod.POST, RequestMethod.PUT })
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void register(@Valid @RequestBody RegisterRequestDto registerRequestDTO, HttpServletResponse response) {
        if(dao.isUserExists(registerRequestDTO.getLogin())) {
            response.setStatus(444);
        } else {
            String encodedToken = passwordEncoder.encode(registerRequestDTO.getToken());
            dao.addUser(new Usr(registerRequestDTO.getLogin(), encodedToken, registerRequestDTO.getBalance()));
            response.setStatus(201);
        }
    }

    @RequestMapping(path = "/client/{login}/balance", method = RequestMethod.GET)
    @ResponseBody
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public BalanceResponseDto balance(@RequestHeader("X-Client-Token") String token, @PathVariable("login") String login, HttpServletResponse response) {
        Usr user = dao.getUserByLoginOrNull(login);
        if(user != null) {
            if(passwordEncoder.matches(token, user.getToken())) {
                response.setStatus(200);
                return new BalanceResponseDto(user.getBalance());
            } else {
                response.setStatus(401);
                return null;
            }
        } else {
            response.setStatus(404);
            return null;
        }
    }
}
