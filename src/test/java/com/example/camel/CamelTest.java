package com.example.camel;

import com.example.camel.routes.CamelDroolsRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.CdiCamelContext;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class CamelTest extends CamelTestSupport {
    @EndpointInject(uri = "mock:result")
    //protected MockEndpoint resultEndpoint;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;

    private WeldContainer container;

    @Test
    public void testSendMatchingMessage() throws Exception {
        Person person = new Person();
        person.setAge(25);

        template.sendBody(person);
        assertThat(person.isAllowedToDrink(), is(true));

    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new CamelDroolsRoute();
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        Weld weld = new Weld();
        container = weld.initialize();

        return container.instance().select(CdiCamelContext.class).get();
    }
}
