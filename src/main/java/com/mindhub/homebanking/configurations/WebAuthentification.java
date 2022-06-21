package com.mindhub.homebanking.configurations;

import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
    public class WebAuthentification extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        ClientRepository clientRepository;

        @Override

        public void init(AuthenticationManagerBuilder auth) throws Exception {

            auth.userDetailsService(inputName -> {

                Client client = clientRepository.findByEmail(inputName);

                if (client != null) {
                    String email = client.getEmail();
                   // String passw = client.getPassword();

                    if(email.contains ("@bank.com")){
                        return new User(client.getEmail(), client.getPassword(), AuthorityUtils.createAuthorityList("ADMIN"));
                    } else {
                        return new User(client.getEmail(), client.getPassword(), AuthorityUtils.createAuthorityList("CLIENT"));
                    }

                } else {

                    throw new UsernameNotFoundException("Unknown client: " + inputName);

                }

            });

        }

        @Bean
        public PasswordEncoder passwordEncoder() {

            return PasswordEncoderFactories.createDelegatingPasswordEncoder();

        }

    }





