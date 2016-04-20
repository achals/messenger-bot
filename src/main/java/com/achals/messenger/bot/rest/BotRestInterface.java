package com.achals.messenger.bot.rest;

/**
 * Created by achalshah on 4/17/16.
 */

import com.google.common.base.Splitter;
import com.google.common.io.CharStreams;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStreamReader;
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
    @Produces(MediaType.APPLICATION_JSON)
    public String webhook_post(@Context HttpServletRequest request)
    {
        final String postData;
        try(final InputStreamReader streamReader = new InputStreamReader(request.getInputStream()))
        {
            postData = CharStreams.toString(streamReader);
            System.out.println(postData);
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }

        return "Error";
    }

}
