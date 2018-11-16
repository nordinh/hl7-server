package com.github.nordinh.hl7server;


import ca.uhn.fhir.context.FhirContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.hl7.fhir.dstu3.hapi.validation.DefaultProfileValidationSupport;
import org.hl7.fhir.dstu3.model.StructureDefinition;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.util.Arrays.stream;

public class TestProfileValidationSupport extends DefaultProfileValidationSupport {

    private static String[] customStructureDefinitionResources = {
            "/profiles/hd-patient.StructureDefinition.xml",
            "/profiles/CRRDBundle.StructureDefinition.xml"
    };

    private Map<String, StructureDefinition> customStructureDefs = new HashMap<>();

    private void initCustomStructureDefs(FhirContext fhirContext) {
        if (!customStructureDefs.isEmpty()) return;

        stream(customStructureDefinitionResources)
                .map(resource -> getCustomStructureDefinition(fhirContext, resource))
                .forEach(structureDefinition -> customStructureDefs.put(structureDefinition.getKey(), structureDefinition.getValue()));
    }


    @Override
    public List<StructureDefinition> fetchAllStructureDefinitions(FhirContext theContext) {
        initCustomStructureDefs(theContext);

        List<StructureDefinition> structureDefinitions = super.fetchAllStructureDefinitions(theContext);
        structureDefinitions.addAll(customStructureDefs.values());
        return structureDefinitions;
    }

    @Override
    public StructureDefinition fetchStructureDefinition(FhirContext theContext, String theUrl) {
        initCustomStructureDefs(theContext);

        return Optional.ofNullable(super.fetchStructureDefinition(theContext, theUrl))
            .orElseGet(() -> customStructureDefs.get(theUrl));
    }

    private Pair<String, StructureDefinition> getCustomStructureDefinition(FhirContext theContext, String customProfile) {
        try {
            StructureDefinition nextSd = theContext.newXmlParser().parseResource(
                    StructureDefinition.class,
                    IOUtils.resourceToString(customProfile, StandardCharsets.UTF_8)
            );
            nextSd.getText().setDivAsString("");

            return Pair.of(nextSd.getUrl(), nextSd);
        } catch (IOException e) {
            throw new RuntimeException("Foutje!!!", e);
        }
    }

}
