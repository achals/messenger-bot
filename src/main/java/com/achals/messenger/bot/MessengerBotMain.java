package com.achals.messenger.bot;

import com.achals.messenger.bot.rest.BotRestModule;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceFilter;
import com.twitter.common.application.AbstractApplication;
import com.twitter.common.application.Lifecycle;
import com.twitter.common.application.modules.HttpModule;
import com.twitter.common.application.modules.LogModule;
import com.twitter.common.application.modules.StatsModule;
import com.twitter.common.net.http.GuiceServletConfig;
import com.twitter.common.net.http.HttpServerDispatch;
import org.mortbay.jetty.servlet.Context;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.logging.Logger;


/**
 * Created by achalshah on 4/17/16.
 */
public final class MessengerBotMain extends AbstractApplication
{
    @Inject
    private Logger logger;

    @Inject
    private Lifecycle lifecycle;

    @Inject
    private HttpServerDispatch httpServer;

    @Inject
    private GuiceServletConfig servletConfig;


    @Override
    public void run()
    {
        logger.info("Service started");

        addRestSupport();

        lifecycle.awaitShutdown();
    }

    @Override
    public Iterable<? extends Module> getModules()
    {
        return Arrays.asList(
                new HttpModule(),
                new LogModule(),
                new BotRestModule(),
                new StatsModule()
        );
    }

    private void addRestSupport()
    {
        Context context = httpServer.getRootContext();
        context.addFilter(GuiceFilter.class, "/pin-bot/*", 0);
        context.addEventListener(servletConfig);
    }

}
