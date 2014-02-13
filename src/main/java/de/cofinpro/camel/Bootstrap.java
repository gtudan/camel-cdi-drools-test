package de.cofinpro.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.CdiCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@Startup
@Singleton
public class Bootstrap {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    private CdiCamelContext camelCtx;

    @Inject
    private Instance<RouteBuilder> routeBuilders;

    @PostConstruct
    public void init() {
        log.info(">> Create CamelContext");

        for (RouteBuilder routeBuilder : routeBuilders) {
            try {
                camelCtx.addRoutes(routeBuilder);
            } catch (Exception e) {
                log.error("Failed to add route definition", e);
            }
        }

        camelCtx.start();
    }

    public void stop() {
        camelCtx.stop();
    }
}
