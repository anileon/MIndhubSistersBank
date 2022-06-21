package com.mindhub.homebanking;

import com.mindhub.homebanking.Controllers.*;
import com.mindhub.homebanking.Repositories.*;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.models.TransactionType.CREDITO;
import static com.mindhub.homebanking.models.TransactionType.DEBITO;

@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepositories, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			// save a couple of customers
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com",passwordEncoder.encode("Melba123"));
			Client client2 = new Client("Pepitos", "Morel", "pepitos@mindhub.com",passwordEncoder.encode("Pepito1"));
			Client client3 = new Client("Miriam", "Martinez", "miriamarti@mindhub.com",passwordEncoder.encode("martinezm12"));
			Client admin1 = new Client ("Admin","admin","admin@bank.com",passwordEncoder.encode("admin001"));
			clientRepositories.save(client1);
			clientRepositories.save(client2);
			clientRepositories.save(client3);
			clientRepositories.save(admin1);

			Account account1 = new Account("VIN001", LocalDateTime.now(), 5000, client1);
			Account account2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 7500, client1);
			Account account3 = new Account("VIN003", LocalDateTime.now(), 6000, client2);
			Account account4 = new Account("VIN004", LocalDateTime.now(), 9000, client2);
			Account account5 = new Account("VIN005", LocalDateTime.now(), 4000, client3);
			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);
			accountRepository.save(account5);

			Transaction transaction1 = new Transaction(5359.13, "Frizzer", LocalDateTime.now(), CREDITO, account1);
			transactionRepository.save(transaction1);
			Transaction transaction2 = new Transaction(1270.31, "Supermarket", LocalDateTime.now(), DEBITO, account1);
			transactionRepository.save(transaction2);
			Transaction transaction3 = new Transaction(6598.59, "Bike", LocalDateTime.now(), CREDITO, account2);
			transactionRepository.save(transaction3);
			Transaction transaction4 = new Transaction(6999.56, "Tv smart", LocalDateTime.now(), DEBITO, account3);
			transactionRepository.save(transaction4);
			Transaction transaction5 = new Transaction(99.58, "Spotify", LocalDateTime.now(), DEBITO, account1);
			transactionRepository.save(transaction5);


			Loan loanHipotecario = new Loan ("Mortgage",500000, List.of(12,24,48,60));
			loanRepository.save(loanHipotecario);
			Loan loanPersonal = new Loan ("Personal",100000, List.of(6,12,24));
			loanRepository.save(loanPersonal);
			Loan loanAutomotriz = new Loan ("Automovil",300000, List.of(6,12,24,36));
			loanRepository.save(loanAutomotriz);



			ClientLoan clientLoan1 = new ClientLoan (400000, 60, client1,loanHipotecario);
			clientLoanRepository.save(clientLoan1);

			ClientLoan clientLoan2 = new ClientLoan (50000, 12, client1,loanPersonal) ;
			clientLoanRepository.save(clientLoan2);

			ClientLoan clientLoan3 = new ClientLoan(100000,24,client2,loanAutomotriz);
			clientLoanRepository.save(clientLoan3);

			ClientLoan clientLoan4 = new ClientLoan(200000,36,client2,loanHipotecario);
			clientLoanRepository.save(clientLoan4);



			Card card1 = new Card (CardType.DEBITO, CardColor.GOLD,client1, LocalDateTime.now(),LocalDateTime.now().plusYears(5),"1893-8743-2449-2392", 545);
			cardRepository.save(card1);

			Card card2 = new Card (CardType.DEBITO, CardColor.TITANIUM,client1, LocalDateTime.now(),LocalDateTime.now().plusYears(5),"1284-6758-2304-3858",464);
			cardRepository.save(card2);

			Card card3 = new Card (CardType.CREDITO, CardColor.SILVER,client1, LocalDateTime.now(),LocalDateTime.now().plusYears(5),"4785-9087-6543-6789",787);
			cardRepository.save(card3);

		};
	}
}
