package com.achals.messenger.bot.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by achalshah on 4/21/16.
 */
@Component
public class ServerCustomizer implements EmbeddedServletContainerCustomizer
{
    public static final String PORT = "PORT";

    @Autowired
    SystemEnvironmentPropertySource environmentPropertySource;

    @Override
    public void customize(final ConfigurableEmbeddedServletContainer container) {
        if (this.environmentPropertySource.containsProperty(PORT))
        {
            final Integer port = Integer.parseInt(this.environmentPropertySource.getProperty(PORT).toString());
            container.setPort(port);
        }
    }

}
