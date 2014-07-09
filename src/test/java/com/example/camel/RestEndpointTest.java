package com.example.camel;

import com.example.camel.rest.ServiceFrontend;
import com.example.camel.routes.CamelDroolsRoute;
import org.apache.camel.cdi.CdiCamelContext;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class RestEndpointTest {

    private ServiceFrontend frontend;

    private Dispatcher dispatcher;
    private static JAXBContext context;

    private Logger log = LoggerFactory.getLogger(getClass());

    @BeforeClass
    public static void initContext() throws JAXBException {
        context = JAXBContext.newInstance(Person.class);
    }


    @Before
    public void setup() throws Exception {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        container.instance().select(CdiCamelContext.class).get().addRoutes(new CamelDroolsRoute());

        frontend = container.instance().select(ServiceFrontend.class).get();
        dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(frontend);

    }

    @Test
    public void testSendingHttpRequest() throws Exception {
        MockHttpRequest request = MockHttpRequest.post("/rest").contentType(MediaType.APPLICATION_XML_TYPE);
        MockHttpResponse response = new MockHttpResponse();

        Person person = new Person();
        person.setAge(25);
        StringWriter writer = new StringWriter();
        context.createMarshaller().marshal(person, writer);
        request.content(writer.toString().getBytes());

        dispatcher.invoke(request, response);
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));

        assertThat(response.getContentAsString(), is("true"));


    }
}
