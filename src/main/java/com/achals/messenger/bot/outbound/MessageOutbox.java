package com.achals.messenger.bot.outbound;

import com.achals.messenger.bot.model.MessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.filter.LoggingFilter;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * Created by achal on 4/21/16.
 */
public class MessageOutbox {

    private static final String MESSAGE_POST_ENDPOINT_FORMAT = "https://graph.facebook.com/v2.6/me/messages?access_token=";

    private final Client client;
    private final ObjectMapper objectMapper;
    private final ExecutorService executorService;
    private final String accessToken;

    @Inject
    public MessageOutbox (final Client client, final ObjectMapper objectMapper, final String accessToken) {
        this.client = client;
        this.objectMapper = objectMapper;
        this.executorService = Executors.newFixedThreadPool(6);
        this.accessToken = accessToken;
    }

    public void send(final MessageResponse messageResponse) {
        this.newPostRunnable(messageResponse).run();
    }

    private Runnable newPostRunnable(final MessageResponse messageResponse) {
        return new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    final WebTarget webTarget = MessageOutbox.this.client.target(MESSAGE_POST_ENDPOINT_FORMAT + MessageOutbox.this.accessToken);
                    webTarget.register(new LoggingFilter());
                    final String responseString = MessageOutbox.this.objectMapper.writeValueAsString(messageResponse);
                    System.out.println(responseString);
                    System.out.println(webTarget.toString());
                    final Response postResponse = webTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(responseString, MediaType.APPLICATION_JSON_TYPE));
                    System.out.println(postResponse.getStatus());
                } catch (final IOException e)
                {
                    e.printStackTrace();
                }
            }
        };
    }
}
