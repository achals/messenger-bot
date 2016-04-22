package com.achals.messenger.bot.outbound;

import com.achals.messenger.bot.model.MessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    final String accessToken = "CAAORld2IHuMBAJksmRRRiYJwXtOvzqLn9VZAnTuYqjVzoq7iGNtdWARchwNa7r2jc2l0omZANN90k0ehZATNpEqmxLIvhuX9ns4L0BCSGz5bBae3wHZBZBS7WlkwpI35tOAxQODn5eY00sB25GYOdsXJoN53HAphhajoZBs9MHzZAboGD7ACtvhSdQC8tKHZCJgZD";

    private final Client client;
    private final ObjectMapper objectMapper;
    private final ExecutorService executorService;

    @Inject
    public MessageOutbox (final Client client, final ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
        this.executorService = Executors.newFixedThreadPool(6);
    }

    public void send(final MessageResponse messageResponse) {
        this.executorService.submit(this.newPostRunnable(messageResponse));
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
                    final String responseString = MessageOutbox.this.objectMapper.writeValueAsString(messageResponse);
                    System.out.println(responseString);
                    final Response postResponse = webTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(responseString, MediaType.APPLICATION_JSON_TYPE));
                    System.out.println(postResponse.toString());
                } catch (final IOException e)
                {
                    e.printStackTrace();
                }
            }
        };
    }
}