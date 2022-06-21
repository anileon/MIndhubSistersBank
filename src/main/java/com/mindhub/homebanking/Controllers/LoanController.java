package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.Repositories.*;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.Services.LoanService;
import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.dto.LoanApplicationDTO;
import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired
    private LoanService loanService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @RequestMapping(path = "/loans",method = RequestMethod.GET)
    public List<LoanDTO> getLoans(){
        return loanService.getLoans();}

    @Transactional
    @RequestMapping(path ="/loans",method = RequestMethod.POST)
    public ResponseEntity<Object> newLoan (Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO){
       Client currentClient = clientService.getClientByEmail(authentication.getName());
       Loan loan = loanService.getLoanById(loanApplicationDTO.getLoanId());
       Account finalAccount = accountService.getAccountByNumber(loanApplicationDTO.getAccountDestiny());

        if (loanApplicationDTO.getAmount() <= 0 || loanApplicationDTO.getPayment() <= 0 || loanApplicationDTO.getAccountDestiny() == null){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (loan == null) {
            return new ResponseEntity<>("Loan unavailable", HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("You have reached the max amount", HttpStatus.FORBIDDEN);
        }

        if (finalAccount == null){
            return new ResponseEntity<>("This account doesn't exist", HttpStatus.FORBIDDEN);
        }

        if(!currentClient.getAccounts().contains(finalAccount)){
            return new ResponseEntity<>("This account doesn't belong to this client", HttpStatus.FORBIDDEN);
        }

        if(!loan.getPayments().contains(loanApplicationDTO.getPayment())){
            return new ResponseEntity<>("payment not available", HttpStatus.FORBIDDEN);
        }

        Transaction loanTransaction = new Transaction(loanApplicationDTO.getAmount(),loan.getName() + "loan aproved", LocalDateTime.now(),TransactionType.CREDITO,finalAccount);
        transactionService.saveTransaction(loanTransaction);

        finalAccount.setBalance(finalAccount.getBalance() + loanTransaction.getAmount());
        accountService.saveAccount(finalAccount);

        ClientLoan clientLoan = new ClientLoan((loanApplicationDTO.getAmount() * 1.20), loanApplicationDTO.getPayment(), currentClient, loan);
        clientLoanRepository.save(clientLoan);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
