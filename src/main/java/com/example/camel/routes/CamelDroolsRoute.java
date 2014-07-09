package com.example.camel.routes;

import org.apache.camel.builder.RouteBuilder;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class CamelDroolsRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:start").autoStartup(true).to("drools:node1/ksession1?action=insertBody")
                .choice()
                .when(simple("${body.isAllowedToDrink}")).log("Can drink")
                .otherwise().log("Not allowed to drink");
    }
}
