package com.example.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.cdi.CdiCamelContext;
import org.drools.grid.GridNode;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class CdiTest {

    private WeldContainer container;
    private CamelContext camelContext;

    @Before
    public void createCamelContext() throws Exception {
        Weld weld = new Weld();
        container = weld.initialize();

        camelContext =  container.instance().select(CdiCamelContext.class).get();
    }

    @Test
    public void testContext() {
        assertThat(camelContext, is(notNullValue()));
    }

    @Test
    public void testLookup() {
        GridNode node1 = camelContext.getRegistry().lookupByNameAndType("node1", GridNode.class);
        assertThat(node1, is(notNullValue()));
    }
}
