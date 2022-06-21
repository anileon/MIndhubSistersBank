package com.mindhub.homebanking.Services.implement;

import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
     public List<ClientDTO> getClientsDTO(){
    return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public Client getClientCurrent(Authentication authentication) {
        return clientRepository.findByEmail(authentication.getName());}

    @Override
    public ClientDTO getClientDto(long id) {
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);}

    @Override
    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    };

}
