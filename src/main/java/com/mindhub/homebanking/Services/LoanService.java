package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.dto.LoanDTO;

import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {

    List<LoanDTO> getLoans();

    Loan getLoanById(long id);

    void saveLoan(Loan loan);

}
