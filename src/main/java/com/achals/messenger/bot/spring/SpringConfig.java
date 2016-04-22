package com.achals.messenger.bot.spring;

import com.achals.messenger.bot.inbound.rest.BotRestInterface;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;

/**
 * Created by achal on 4/21/16.
 */
@Configuration
public class SpringConfig {

    @Bean
    public Client client()
    {
        return new JerseyClientBuilder().build();
    }

    @Bean
    public ObjectMapper objectMapper()
    {
        return new ObjectMapper();
    }

    @Bean
    public BotRestInterface botRestInterface()
    {
        return new BotRestInterface();
    }
}
