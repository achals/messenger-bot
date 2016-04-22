package com.achals.messenger.bot.spring;

import com.achals.messenger.bot.inbound.rest.BotRestInterface;
import com.achals.messenger.bot.outbound.MessageOutbox;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import javax.ws.rs.client.Client;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by achal on 4/21/16.
 */
@Configuration
public class SpringConfig
{

    public static final String PORT = "PORT";
    public static final int DEFAULT_PORT = 8080;
    public static final String VALIDATION_TOKEN_KEY = "VALIDATION_TOKEN";
    public static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN";

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
        return new BotRestInterface(this.validationToken(), this.messageOutbox());
    }

    @Bean
    public MessageOutbox messageOutbox()
    {
        return new MessageOutbox(this.client(), this.objectMapper(), this.accessToken());
    }

    @Bean
    public SystemEnvironmentPropertySource environmentPropertySource()
    {
        final Map<String, Object> env = new HashMap<>(System.getenv());
        return new SystemEnvironmentPropertySource("Environment", env);
    }

    @Bean
    public int port()
    {
        if (this.environmentPropertySource().containsProperty(PORT))
        {
            final Integer port = Integer.parseInt(this.environmentPropertySource().getProperty(PORT).toString());
            return port;
        }
        return DEFAULT_PORT;
    }

    @Bean
    public String accessToken()
    {
        if (this.environmentPropertySource().containsProperty(ACCESS_TOKEN_KEY))
        {
            return this.environmentPropertySource().getProperty(ACCESS_TOKEN_KEY).toString();
        }
        return "MISSING_ACCESS_TOKEN";
    }

    @Bean
    public String validationToken()
    {
        if (this.environmentPropertySource().containsProperty(VALIDATION_TOKEN_KEY))
        {
            return this.environmentPropertySource().getProperty(VALIDATION_TOKEN_KEY).toString();
        }
        return "MISSING_VALIDATION_TOKEN";
    }
}
