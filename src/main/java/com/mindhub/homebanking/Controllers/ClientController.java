package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.Repositories.AccountRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import static com.mindhub.homebanking.Utils.Utils.getRandomNumber;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private  ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClientDTO> getClients (){
        return clientService.getClientsDTO();
    }

    @GetMapping("clients/{id}") //asigna una ruta a un especifico controlador
    public ClientDTO getClient (@PathVariable Long id){
        //estoy asignando una anotacion llamada path variable
        //path variable..va a tomar la variable de la clase en la que se le especifique
        return clientService.getClientDto(id);
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientService.getClientByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client clientRegistered = new Client(firstName,lastName,email,passwordEncoder.encode(password));
        clientService.saveClient(clientRegistered);

        Account account = new Account("VIN-" + getRandomNumber(0,99999999), LocalDateTime.now(),0, clientRegistered);
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication) {
        return new ClientDTO(clientService.getClientCurrent(authentication));
    }
}


