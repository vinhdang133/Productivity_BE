package com.productivity.web.service.implement;


import com.productivity.web.dto.request.ChangePasswordRequest;
import com.productivity.web.dto.request.LoginRequest;
import com.productivity.web.dto.request.RegisterRequest;
import com.productivity.web.dto.request.UpdateAccountRequest;
import com.productivity.web.dto.response.AccountResponse;
import com.productivity.web.dto.response.RegisterResponse;
import com.productivity.web.entity.Account;
import com.productivity.web.repository.AccountRepository;
import com.productivity.web.service.AccountServiceInterface;
import com.productivity.web.service.AuthenServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements  AccountServiceInterface {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    public AccountResponse getCurrentAccount(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Current account not found"));

        return mapToAccountResponse(account);
    }

    @Override
    public AccountResponse getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Current account not found"));

        return mapToAccountResponse(account);
    }

    @Override
    public AccountResponse getAccountByEmail(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found with email: " + email));

        return mapToAccountResponse(account);
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(this::mapToAccountResponse)
                .toList();
    }


    @Override
    public AccountResponse updateProfile(String email, UpdateAccountRequest request) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Current account not found"));

        if (request.getDisplayName() != null && !request.getDisplayName().isBlank()) {
            account.setDisplayName(request.getDisplayName());
        }

        if (request.getAvatarUrl() != null && !request.getAvatarUrl().isBlank()) {
            account.setAvartarUrl(request.getAvatarUrl());
        }

        Account updatedAccount = accountRepository.save(account);

            return mapToAccountResponse(updatedAccount);

    }

    @Override
    public void changePassword(String email, ChangePasswordRequest request) {
            Account account = accountRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Account not found"));

            boolean oldPasswordMatches = passwordEncoder.matches(
                    request.getOldPassword(),
                    account.getPasswordHash()

            );
            if (oldPasswordMatches) {
                throw new RuntimeException("Old password does not match");
            }

            String newPassword = request.getNewPassword();
            account.setPasswordHash(passwordEncoder.encode(newPassword));
            accountRepository.save(account);

            //lấy mk cũ vaf  mới trong request và so sánh mk cũ r với mk cũ account nếu đúng thì lưu lại vào db


    }

    @Override
    public void deactivateAccount(String email) {
        Account account = findAccountByEmail(email);

        account.setActive(false);

        accountRepository.save(account);
    }


    private Account findAccountByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found with email: " + email));
    }
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
