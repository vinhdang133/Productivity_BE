package com.productivity.web.controller;


import com.productivity.web.dto.request.ChangePasswordRequest;
import com.productivity.web.dto.request.UpdateAccountRequest;
import com.productivity.web.dto.response.AccountResponse;
import com.productivity.web.repository.AccountRepository;
import com.productivity.web.service.implement.AccountServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;
    private final AccountServiceImp accountService;

    @GetMapping("/me")
    public AccountResponse getCurrentAccount(Authentication authentication) {

        String email = authentication.getName();
        return accountService.getAccountByEmail(email);


    }
    @GetMapping("/ad/getAllAccount")
    public List<AccountResponse> getAllAccount(){
        return accountService.getAllAccounts();

    }

    @PutMapping("/me/updateAccount")
        public AccountResponse updateProfile(
                Authentication authentication,
                @RequestBody @Valid UpdateAccountRequest request)
    {
        String email = authentication.getName();
        return accountService.updateProfile(email, request);
    }



    @PutMapping("/me/password")
    public String changePassword(
        Authentication authentication,
                @Valid @RequestBody ChangePasswordRequest request)
    {
        String email = authentication.getName();
        accountService.changePassword(email, request);
        return "Password changed successfully";

    }

    @PatchMapping("/me/deactivate")
        public String deactivateAccount(Authentication authentication) {
        String email = authentication.getName();
        accountService.deactivateAccount(email);
        return "Account deactivated successfully";
        }

}
