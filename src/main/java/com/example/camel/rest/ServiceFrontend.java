package com.example.camel.rest;

import com.example.camel.Person;
import org.apache.camel.*;
import org.apache.camel.cdi.Uri;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

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
        return person.isAllowedToDrink();
    }
}
