package com.mindhub.homebanking.Controllers;


import com.mindhub.homebanking.Repositories.AccountRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Repositories.TransactionRepository;

import com.mindhub.homebanking.dto.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;



@RestController

@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;


    @RequestMapping("/transactions")
    public List<TransactionDTO>getTransaction(){
        return transactionRepository.findAll().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toList());
    }

    @RequestMapping("/transactions/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id) {
        return transactionRepository.findById(id).map(transaction -> new TransactionDTO(transaction)).orElse(null);
    }

    @Transactional
    @RequestMapping(path ="/client/current/transactions",method = RequestMethod.POST)
    public ResponseEntity<Object> newTransaction (Authentication authentication, @RequestParam double amount,
                                                  @RequestParam String description, @RequestParam String newAccountOrigen,
                                                  @RequestParam String newAccountDestiny) {

        Account accountOrigen = accountRepository.findByNumber(newAccountOrigen);
        Account accountDestiny = accountRepository.findByNumber(newAccountDestiny);
        Client currentClient = clientRepository.findByEmail(authentication.getName());

        if (amount == 0 || description.isEmpty() || newAccountOrigen.isEmpty() || newAccountDestiny.isEmpty()) {
            return new ResponseEntity<>("You don't have access to this transaction yet", HttpStatus.FORBIDDEN);
        }

        if (accountDestiny.getNumber().equals(accountOrigen.getNumber())){
            return new ResponseEntity<>("You cannot send to the same account",HttpStatus.FORBIDDEN);
        }

        if (!currentClient.getAccounts().contains(accountOrigen)){
            return new ResponseEntity<>("You are not the account owner", HttpStatus.FORBIDDEN);
        }

        if (accountDestiny == null){
            return new ResponseEntity<>("This account doesn't exist", HttpStatus.FORBIDDEN);
        }

        if (accountOrigen.getBalance() < amount){
            return new ResponseEntity<>("You don't have enough money in your account", HttpStatus.FORBIDDEN);
        }


        accountOrigen.setBalance(accountOrigen.getBalance() - amount);
        accountRepository.save(accountOrigen);

        accountDestiny.setBalance(accountDestiny.getBalance() + amount);
        accountRepository.save(accountDestiny);


        Transaction debitTransaction = new Transaction(amount,description,LocalDateTime.now(),TransactionType.DEBITO, accountOrigen);
        transactionRepository.save(debitTransaction);
        Transaction creditTransaction = new Transaction(amount,description,LocalDateTime.now(),TransactionType.CREDITO, accountDestiny);
        transactionRepository.save(creditTransaction);

        return new ResponseEntity<>("Transaction successful", HttpStatus.CREATED);

    }
}
