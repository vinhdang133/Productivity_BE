package com.productivity.web.controller;


import com.productivity.web.dto.response.AccountResponse;
import com.productivity.web.repository.AccountRepository;
import com.productivity.web.service.implement.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @GetMapping("/me")
    public AccountResponse getCurrentAccount(Authentication authentication) {

        String email = authentication.getName();
        return accountService.getAccountByEmail(email);


    }
}
