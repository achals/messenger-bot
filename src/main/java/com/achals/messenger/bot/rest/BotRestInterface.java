package com.achals.messenger.bot.rest;

/**
 * Created by achalshah on 4/17/16.
 */

import com.achals.messenger.bot.model.MessagePost;
import com.google.common.base.Splitter;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Map;

@Path("/pin-bot/v1")
public class BotRestInterface
{
    final String validationToken = "bd4e2912-35e2-41fc-a15f-7df1fce6c131";
    final String accessToken = "CAAORld2IHuMBACdylvThBuZCzKfZBKMmPaLakJGXmYN5LyufuRdH7YpsQhOpayMusk6kVYlCVKoOpZCpnvF2EZActnaMrlI0KkjGSwoUAikcd9dVYHf7yz2ZBKI6ZCOfZAWN8L19cjNj2hB2fVqZAlv7FUEoHZCcK5SaIWiEyNq7rG6CLez7PgfAKSPABdZA4oto8ZD";

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
        System.out.println(messagePost);
        return Response.ok().build();
    }

}
