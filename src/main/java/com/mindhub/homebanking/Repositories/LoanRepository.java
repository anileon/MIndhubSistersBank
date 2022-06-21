package com.mindhub.homebanking.Repositories;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan,Long> {
    Loan findById(Loan id);
}
