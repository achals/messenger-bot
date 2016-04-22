package com.achals.messenger.bot.spring;

import com.achals.messenger.bot.inbound.rest.BotRestInterface;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Created by achal on 4/21/16.
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(BotRestInterface.class);
    }
}
