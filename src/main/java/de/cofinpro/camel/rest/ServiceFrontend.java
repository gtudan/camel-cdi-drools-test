package de.cofinpro.camel.rest;

import de.cofinpro.camel.Person;
import org.apache.camel.*;
import org.apache.camel.cdi.Uri;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
@Path("/rest")
public class ServiceFrontend {

    @Inject @Uri("direct:start")
    private ProducerTemplate template;

    @POST
    public boolean canDrink(Person person) {
        template.sendBody(person);
        return person.canDrink();
    }
}
