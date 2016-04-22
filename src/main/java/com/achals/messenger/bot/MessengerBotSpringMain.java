package com.achals.messenger.bot;

import com.sun.jersey.api.client.Client;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by achal on 4/21/16.
 */
@SpringBootApplication
public class MessengerBotSpringMain {

    public static void main (final String[] args) throws Exception {
        SpringApplication.run(MessengerBotSpringMain.class, args);
    }
}
