package com.example.camel;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.grid.GridNode;
import org.drools.grid.impl.GridNodeImpl;
import org.drools.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class DroolsComponentProducer {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Produces
    @Named("node1")
    public GridNode initSession() {
        log.info("Creating drools kbase");
        GridNode node = new GridNodeImpl();

        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("testRule.drl"), ResourceType.DRL);

        if (kbuilder.hasErrors()) {
            if (kbuilder.getErrors().size() > 0) {
                for (KnowledgeBuilderError kerror : kbuilder.getErrors()) {
                    log.error(kerror.getMessage());
                }
                throw new RuntimeException(kbuilder.getErrors().toString());
            }
        }
        node.set("ksession1", kbuilder.newKnowledgeBase().newStatelessKnowledgeSession());

        return node;
    }

}
