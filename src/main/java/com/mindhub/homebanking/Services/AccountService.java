package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.dto.AccountsDTO;
import com.mindhub.homebanking.models.Account;


import java.util.List;

public interface AccountService {

    List<AccountsDTO> getAccountsDTO();

    Account getAccount(long id);

    AccountsDTO getAccountDTO(long id);

    Account getAccountByNumber(String number);

    void saveAccount(Account account);


}
