package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.Repositories.AccountRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.dto.AccountsDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.Utils.Utils.getRandomNumber;
import static java.util.stream.Collectors.toList;

    @RestController //anotacion..van arriba de lo que yo quiero trabajar de esas anotaciones
    @RequestMapping("/api")

    public class AccountController {
        @Autowired //inyeccion de dependencia
        private AccountService accountService;

        @Autowired
        private ClientService clientService;

        @RequestMapping("/accounts")
        public List<AccountsDTO> getAccounts() {
            return accountService.getAccountsDTO();
        }

        @RequestMapping("/accounts/{id}") //asigna una ruta a un especifico controlador
        public AccountsDTO getAccount(@PathVariable Long id) {
            // path variable..va a tomar la variable de la clase en la que se le especifique
            return accountService.getAccountDTO(id);
        }

        @PostMapping("/clients/current/accounts")
        public ResponseEntity<Object> accountCreation(Authentication authentication) {
            LocalDateTime creationDate = LocalDateTime.now();
            double balance = 0;

            Client client = clientService.getClientByEmail(authentication.getName());

            if (client.getAccounts().size() >= 3) {
                return new ResponseEntity<>("You have reached the max number of accounts", HttpStatus.FORBIDDEN);}
            String randomNumber = "VIN-" + getRandomNumber(0, 99999999);

            accountService.saveAccount(new Account(randomNumber, creationDate, balance, client));

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

