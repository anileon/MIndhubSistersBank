package com.mindhub.homebanking.Services.implement;

import com.mindhub.homebanking.Repositories.AccountRepository;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.dto.AccountsDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<AccountsDTO> getAccountsDTO() {
        return accountRepository.findAll().stream().map(AccountsDTO::new).collect(toList());
    }

    @Override
    public Account getAccount(long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public AccountsDTO getAccountDTO(long id) {
        return accountRepository.findById(id).map(AccountsDTO::new).orElse(null);
    }

    @Override
    public Account getAccountByNumber(String number) {
       return accountRepository.findByNumber(number);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

}
