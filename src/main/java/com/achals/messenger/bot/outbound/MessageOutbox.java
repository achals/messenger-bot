package com.achals.messenger.bot.outbound;

import com.achals.messenger.bot.model.MessageResponse;
import com.sun.jersey.api.client.AsyncWebResource;
import com.sun.jersey.api.client.Client;
import org.codehaus.jackson.map.ObjectMapper;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by achal on 4/21/16.
 */
public class MessageOutbox {

    private static final String MESSAGE_POST_ENDPOINT_FORMAT = "https://graph.facebook.com/v2.6/me/messages?access_token=";

    final String accessToken = "CAAORld2IHuMBAJksmRRRiYJwXtOvzqLn9VZAnTuYqjVzoq7iGNtdWARchwNa7r2jc2l0omZANN90k0ehZATNpEqmxLIvhuX9ns4L0BCSGz5bBae3wHZBZBS7WlkwpI35tOAxQODn5eY00sB25GYOdsXJoN53HAphhajoZBs9MHzZAboGD7ACtvhSdQC8tKHZCJgZD";

    private final Client client;
    private final ObjectMapper objectMapper;


    @Inject
    public MessageOutbox (final Client client, final ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public void send(final MessageResponse messageResponse) {
        try {
            final AsyncWebResource webResource = this.client.asyncResource(MESSAGE_POST_ENDPOINT_FORMAT + this.accessToken);
            final String responseString = this.objectMapper.writeValueAsString(messageResponse);
            System.out.println(responseString);
            final Future<?> postResponse = webResource.type(MediaType.APPLICATION_JSON_TYPE)
                    .post(responseString);
            System.out.println(postResponse.isDone());
            System.out.println(postResponse.get());
        } catch(final InterruptedException | ExecutionException | IOException e)
        {
            e.printStackTrace();
        }
    }
}
