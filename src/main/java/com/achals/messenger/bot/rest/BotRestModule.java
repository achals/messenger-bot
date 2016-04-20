package com.achals.messenger.bot.rest;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import org.glassfish.jersey.servlet.ServletContainer;

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
        bind(ServletContainer.class).in(Singleton.class);

        serve("/*").with(ServletContainer.class);
        // Serve all URLs through Guice
        //serve("/*").with(GuiceContainer.class);

        // The actual REST Endpoints
        bind(BotRestInterface.class).in(Singleton.class);
    }

}
