package com.achals.messenger.bot.rest;

/**
 * Created by achalshah on 4/17/16.
 */

import com.achals.messenger.bot.model.MessagePost;
import com.achals.messenger.bot.model.MessageResponse;
import com.google.common.base.Splitter;
import com.google.common.io.CharStreams;
import com.sun.jersey.api.client.AsyncWebResource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.LoggingFilter;
import org.aopalliance.intercept.Invocation;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Path("/pin-bot/v1")
public class BotRestInterface
{
    private static final String MESSAGE_POST_ENDPOINT_FORMAT = "https://graph.facebook.com/v2.6/me/messages?access_token=";

    final String validationToken = "bd4e2912-35e2-41fc-a15f-7df1fce6c131";
    final String accessToken = "CAAORld2IHuMBAJksmRRRiYJwXtOvzqLn9VZAnTuYqjVzoq7iGNtdWARchwNa7r2jc2l0omZANN90k0ehZATNpEqmxLIvhuX9ns4L0BCSGz5bBae3wHZBZBS7WlkwpI35tOAxQODn5eY00sB25GYOdsXJoN53HAphhajoZBs9MHzZAboGD7ACtvhSdQC8tKHZCJgZD";

    private final Client client;
    private final ObjectMapper objectMapper;

    @Inject
    public BotRestInterface(final Client client, final ObjectMapper objectMapper)
    {
        this.client = client;
        this.client.addFilter(new LoggingFilter(System.out));
        this.objectMapper = objectMapper;
    }

    @GET
    @Path("health")
    @Produces(MediaType.TEXT_PLAIN)
    public String health()
    {
        return "healthy";
    }


    @GET
    @Path("webhook")
    @Produces(MediaType.TEXT_PLAIN)
    public String webhook_get(@Context HttpServletRequest request)
    {
        final Splitter.MapSplitter splitter = Splitter.on("&")
                .trimResults()
                .withKeyValueSeparator("=");
        final String queryString = request.getQueryString();
        if (queryString != null)
        {
            final Map<String, String> queryParams = splitter.split(queryString);

            if (queryParams.get("hub.verify_token").equals(this.validationToken))
            {
                return queryParams.get("hub.challenge");
            }
        }
        return "Error, wrong validation token";
    }

    @POST
    @Path("webhook")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response webhook_post(final MessagePost messagePost)
    {

        final AsyncWebResource webResource = this.client.asyncResource(MESSAGE_POST_ENDPOINT_FORMAT + this.accessToken);
        final MessageResponse response = new MessageResponse();
        response.recipient = messagePost.entry.get(0).messaging.get(0).sender.id;
        response.message = new MessageResponse.MessageData();
        response.message.text = messagePost.entry.get(0).messaging.get(0).message.text;
        try
        {
            final String messagePostString = this.objectMapper.writeValueAsString(messagePost);
            System.out.println(messagePostString);

            final String responseString = this.objectMapper.writeValueAsString(response);
            System.out.println(responseString);
            final Future<?> postResponse = webResource.type(MediaType.APPLICATION_JSON_TYPE)
                    .post(responseString);
            System.out.println(postResponse.isDone());
            System.out.println(postResponse.get());
        }
        catch(final InterruptedException | ExecutionException | IOException e)
        {
            e.printStackTrace();
        }
        return Response.ok().build();
    }

}
