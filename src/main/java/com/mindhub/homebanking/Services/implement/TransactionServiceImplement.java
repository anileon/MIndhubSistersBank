package com.mindhub.homebanking.Services.implement;

import com.mindhub.homebanking.Repositories.TransactionRepository;
import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransactionServiceImplement implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

}
