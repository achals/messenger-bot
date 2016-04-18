package com.achals.messenger.bot.rest;

/**
 * Created by achalshah on 4/17/16.
 */

import com.achals.messenger.bot.model.Message;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/pin-bot/v1")
@Produces(MediaType.APPLICATION_JSON)
public class BotRestInterface
{
    @GET
    @Path("health")
    public Message health()
    {
        return new Message("healthy");
    }

}
