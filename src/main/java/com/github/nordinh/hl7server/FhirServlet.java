package com.github.nordinh.hl7server;

import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns= {"/fhir/*"}, displayName="FHIR FhirServlet")
public class FhirServlet extends RestfulServer {

    private static final long serialVersionUID = 1L;

    /**
     * The initialize method is automatically called when the servlet is starting up, so it can
     * be used to configure the servlet to define resource providers, or set up
     * configuration, interceptors, etc.
     */
    @Override
    protected void initialize() throws ServletException {
        List<IResourceProvider> resourceProviders = new ArrayList<>();
        setResourceProviders(resourceProviders);
    }
}
