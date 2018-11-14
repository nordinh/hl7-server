package com.github.nordinh.hl7server;

import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns= {"/fhir/*"}, displayName="FHIR FhirServlet")
public class FhirServlet extends RestfulServer {

    private static final long serialVersionUID = 1L;

    @Override
    protected void initialize() {
        List<IResourceProvider> resourceProviders = new ArrayList<>();
        resourceProviders.add(new PatientResourceProvider());
        setResourceProviders(resourceProviders);
    }
}
