package com.productivity.web.service;

import com.productivity.web.dto.request.ChangePasswordRequest;
import com.productivity.web.dto.request.UpdateAccountRequest;
import com.productivity.web.dto.response.AccountResponse;
import com.productivity.web.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountServiceInterface {

    AccountResponse getCurrentAccount(String email);

    AccountResponse getAccountById(Long id);

    AccountResponse getAccountByEmail(String email);

    List<AccountResponse> getAllAccounts();

    AccountResponse updateProfile(String email, UpdateAccountRequest request);

    void changePassword(String email, ChangePasswordRequest request);

    void deactivateAccount(String email);

    private AccountResponse mapToAccountResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .email(account.getEmail())
                .displayName(account.getDisplayName())
                .avatarUrl(account.getAvartarUrl())
                .active(account.isActive())
                .createdAt(account.getCreatedAt())
                .build();
    }

}
