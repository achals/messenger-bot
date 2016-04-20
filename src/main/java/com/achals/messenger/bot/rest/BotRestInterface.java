package com.achals.messenger.bot.rest;

/**
 * Created by achalshah on 4/17/16.
 */

import com.google.common.base.Splitter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/pin-bot/v1")
@Produces(MediaType.TEXT_PLAIN)
public class BotRestInterface
{
    final String validationToken = "bd4e2912-35e2-41fc-a15f-7df1fce6c131";

    @GET
    @Path("health")
    public String health()
    {
        return "healthy";
    }


    @GET
    @Path("webhook")
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
    public String webhook_post(@Context HttpServletRequest request)
    {
        return "webhook_post";
    }

}
