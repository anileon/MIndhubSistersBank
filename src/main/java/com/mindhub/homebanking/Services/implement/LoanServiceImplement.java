package com.mindhub.homebanking.Services.implement;

import com.mindhub.homebanking.Repositories.LoanRepository;
import com.mindhub.homebanking.Services.LoanService;
import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LoanServiceImplement implements LoanService {
    @Autowired
    LoanRepository loanRepository;

    @Override
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(toList());
    }

    @Override
    public Loan getLoanById(long id) {
       return loanRepository.findById(id).orElse(null);
    }


    @Override
    public void saveLoan(Loan loan) {
         loanRepository.save(loan);

    }
}
