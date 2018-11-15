package com.github.nordinh.hl7server;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;
import org.hl7.fhir.dstu3.hapi.validation.FhirInstanceValidator;
import org.hl7.fhir.dstu3.model.Enumerations;
import org.hl7.fhir.dstu3.model.OperationOutcome;
import org.hl7.fhir.dstu3.model.Patient;
import org.junit.Test;

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
                "        <system value=\"http://healthdata.be/ssid\"></system>\n" +
                "        <value value=\"12345678901\"></value>\n" +
                "    </identifier>\n" +
                "    <name>\n" +
                "        <family value=\"Test\"></family>\n" +
                "        <given value=\"PatientOne\"></given>\n" +
                "    </name>\n" +
                "    <gender value=\"female\"></gender>\n" +
                "</Patient>";

        ValidationResult result = validator.validateWithResult(patient);

        System.out.println(result.isSuccessful()); // false

        for (SingleValidationMessage next : result.getMessages()) {
            System.out.println(" Next issue " + next.getSeverity() + " - " + next.getLocationString() + " - " + next.getMessage());
        }
        OperationOutcome oo = (OperationOutcome) result.toOperationOutcome();
    }
}
