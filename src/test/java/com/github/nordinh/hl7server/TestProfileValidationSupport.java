package com.github.nordinh.hl7server;


import ca.uhn.fhir.context.FhirContext;
import javafx.util.Pair;
import org.hl7.fhir.dstu3.hapi.validation.DefaultProfileValidationSupport;
import org.hl7.fhir.dstu3.model.StructureDefinition;

import java.io.InputStreamReader;
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
        InputStreamReader reader = new InputStreamReader(DefaultProfileValidationSupport.class
            .getResourceAsStream("/profiles/hd-patient.StructureDefinition.xml"));

        StructureDefinition nextSd = theContext.newXmlParser().parseResource(StructureDefinition.class, reader);
        nextSd.getText().setDivAsString("");

        return new Pair<>(nextSd.getUrl(), nextSd);
    }

}
