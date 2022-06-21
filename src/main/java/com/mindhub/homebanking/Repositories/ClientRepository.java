package com.mindhub.homebanking.Repositories;

import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource //servicio rest automatic, de la data que se esta generando
public interface ClientRepository extends JpaRepository <Client,Long> //retoma tu lista de clientes//
 {
  Client findByEmail(String email);
}
