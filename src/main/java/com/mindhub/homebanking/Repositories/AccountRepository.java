package com.mindhub.homebanking.Repositories;


import com.mindhub.homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

    @RepositoryRestResource //servicio rest automatic, de la data que se esta generando
    public interface AccountRepository extends JpaRepository<Account,Long> //retoma tu lista de clientes//extends (herencia) jpa(padre)(account(hijo) me extiende los atributos y metodos de lo que le pida
    {
        Account findByNumber(String number);

    }

