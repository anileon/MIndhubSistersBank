package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;
import java.util.List;

public interface ClientService {

    List<ClientDTO> getClientsDTO();

    Client getClientCurrent(Authentication authentication);

    ClientDTO getClientDto(long id);

    Client getClientByEmail(String email);

    void saveClient(Client client);

}
