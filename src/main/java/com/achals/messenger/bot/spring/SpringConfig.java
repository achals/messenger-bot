package com.achals.messenger.bot.spring;

import com.achals.messenger.bot.rest.BotRestInterface;
import com.sun.jersey.api.client.Client;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by achal on 4/21/16.
 */
@Configuration
public class SpringConfig {

    @Bean
    public Client client()
    {
        return Client.create();
    }

    @Bean
    public ObjectMapper objectMapper()
    {
        return new ObjectMapper();
    }

    @Bean
    public BotRestInterface botRestInterface()
    {
        return new BotRestInterface(this.client(), this.objectMapper());
    }
}
