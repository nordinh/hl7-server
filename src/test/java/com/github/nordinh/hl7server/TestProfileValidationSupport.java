package com.github.nordinh.hl7server;


import ca.uhn.fhir.context.FhirContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.hl7.fhir.dstu3.hapi.validation.DefaultProfileValidationSupport;
import org.hl7.fhir.dstu3.model.StructureDefinition;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class TestProfileValidationSupport extends DefaultProfileValidationSupport {

    @Override
    public List<StructureDefinition> fetchAllStructureDefinitions(FhirContext theContext) {
        List<StructureDefinition> structureDefinitions = super.fetchAllStructureDefinitions(theContext);
        structureDefinitions.add(getCustomStructureDefinition(theContext).getValue());
        return structureDefinitions;
    }

    @Override
    public StructureDefinition fetchStructureDefinition(FhirContext theContext, String theUrl) {
        return Optional.ofNullable(super.fetchStructureDefinition(theContext, theUrl))
            .orElseGet(() -> getCustomStructureDefinition(theContext).getValue());
    }

    private Pair<String, StructureDefinition> getCustomStructureDefinition(FhirContext theContext) {
        try {
            StructureDefinition nextSd = theContext.newXmlParser().parseResource(
                    StructureDefinition.class,
                    IOUtils.resourceToString("/profiles/hd-patient.StructureDefinition.xml", StandardCharsets.UTF_8)
            );
            nextSd.getText().setDivAsString("");

            return Pair.of(nextSd.getUrl(), nextSd);
        } catch (IOException e) {
            throw new RuntimeException("Foutje!!!", e);
        }
    }

}
