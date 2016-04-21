package com.achals.messenger.bot.rest;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

/**
 * Created by achalshah on 4/17/16.
 */
public class BotRestModule extends ServletModule
{

    @Override
    protected void configureServlets()
    {
        super.configureServlets();

        // JSON mapper, maps JSON to/from POJOs
        bind(JacksonJsonProvider.class).in(Singleton.class);

        // Serve all URLs through Guice
        serve("/*").with(GuiceContainer.class);

        // The actual REST Endpoints
        bind(BotRestInterface.class).in(Singleton.class);
    }

}
