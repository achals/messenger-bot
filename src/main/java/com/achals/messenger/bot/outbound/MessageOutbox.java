package com.achals.messenger.bot.outbound;

import com.achals.messenger.bot.model.MessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by achal on 4/21/16.
 */
public class MessageOutbox {

    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final String MESSAGE_POST_ENDPOINT_FORMAT = "https://graph.facebook.com/v2.6/me/messages?access_token=";
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private final ExecutorService executorService;
    private final String accessToken;

    @Inject
    public MessageOutbox (final OkHttpClient client, final ObjectMapper objectMapper, final ExecutorService executorService, final String accessToken) {
        this.client = client;
        this.objectMapper = objectMapper;
        this.executorService = executorService;
        this.accessToken = accessToken;
    }

    public void send (final MessageResponse messageResponse) {
        this.executorService.submit(this.newPostRunnable(messageResponse));
    }

    private Runnable newPostRunnable (final MessageResponse messageResponse) {
        return () -> {
            try {
                final String responseString = MessageOutbox.this.objectMapper.writeValueAsString(messageResponse);

                RequestBody requestBody = RequestBody.create(JSON_MEDIA_TYPE, responseString);
                final Request request = new Request.Builder().url(MESSAGE_POST_ENDPOINT_FORMAT + MessageOutbox.this.accessToken).post(requestBody).build();
                final Response response = MessageOutbox.this.client.newCall(request).execute();
                System.out.println(response.toString());
            } catch (final IOException e) {
                e.printStackTrace();
            }

        };
    }
}
