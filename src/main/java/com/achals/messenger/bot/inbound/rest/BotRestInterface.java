package com.achals.messenger.bot.inbound.rest;

/**
 * Created by achalshah on 4/17/16.
 */

import com.achals.messenger.bot.model.MessagePost;
import com.achals.messenger.bot.model.MessageResponse;
import com.achals.messenger.bot.outbound.MessageOutbox;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;


@RestController
@RequestMapping("/pin-bot/v1")
public class BotRestInterface
{
    private final String validationToken;
    private final MessageOutbox outbox;

    @Inject
    public BotRestInterface(final String validationToken, final MessageOutbox outbox)
    {
        this.validationToken = validationToken;
        this.outbox = outbox;
    }

    @RequestMapping(value = "health", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN)
    public String health()
    {
        return "healthy";
    }

    @RequestMapping(value = "webhook", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN)
    public String webhook_get(@RequestParam("hub.verify_token") final String verify_token,
                              @RequestParam("hub.challenge") final String challenge)
    {
        if (this.validationToken.equals(verify_token) && challenge != null)
        {
            return challenge;
        }

        return "Error, wrong validation token";
    }

    @RequestMapping(value = "webhook", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON,produces = MediaType.APPLICATION_JSON)
    public void webhook_post(@RequestBody final MessagePost messagePost)
    {
        final MessageResponse response = new MessageResponse();
        response.recipient = new MessageResponse.Recipient();
        response.recipient.id = messagePost.entry.get(0).messaging.get(0).sender.id;
        response.message = new MessageResponse.MessageData();
        response.message.text = messagePost.entry.get(0).messaging.get(0).message.text;

        this.outbox.send(response);
    }

}
