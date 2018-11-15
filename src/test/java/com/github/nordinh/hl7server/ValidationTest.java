package com.github.nordinh.hl7server;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;
import org.hl7.fhir.dstu3.hapi.validation.FhirInstanceValidator;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ValidationTest {

    @Test
    public void testValidation() {
        FhirContext ctx = FhirContext.forDstu3();

        FhirValidator validator = ctx.newValidator();
        FhirInstanceValidator instanceValidator = new FhirInstanceValidator(new TestProfileValidationSupport());
        validator.registerValidatorModule(instanceValidator);

        instanceValidator.setAnyExtensionsAllowed(true);

        String patient = "<Patient xmlns=\"http://hl7.org/fhir\">\n" +
                "    <meta>\n" +
                "        <profile value=\"http://healthdata.be/fhir/StructureDefinition/hd-patient\"/>\n" +
                "    </meta>" +
                "    <identifier>\n" +
                "        <system value=\"http://healthdata.be/ssin\"></system>\n" +
                "        <value value=\"12345678901\"></value>\n" +
                "    </identifier>\n" +
                "    <name>\n" +
                "        <family value=\"Test\"></family>\n" +
                "        <given value=\"PatientOne\"></given>\n" +
                "    </name>\n" +
                "    <gender value=\"female\"></gender>\n" +
                "</Patient>";

        ValidationResult result = validator.validateWithResult(patient);

        assertThat(result.isSuccessful(), is(true));

        for (SingleValidationMessage next : result.getMessages()) {
            System.out.println(" Next issue " + next.getSeverity() + " - " + next.getLocationString() + " - " + next.getMessage());
        }
    }
}
