package com.github.nordinh.hl7server;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;
import org.apache.commons.io.IOUtils;
import org.hl7.fhir.dstu3.hapi.validation.FhirInstanceValidator;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ValidationTest {

    private FhirValidator validator;

    @Before
    public void setup() {
        FhirContext ctx = FhirContext.forDstu3();

        validator = ctx.newValidator();
        FhirInstanceValidator instanceValidator = new FhirInstanceValidator(new TestProfileValidationSupport());
        validator.registerValidatorModule(instanceValidator);

        instanceValidator.setAnyExtensionsAllowed(true);
    }

    @Test
    public void testValidation_SingleSSIN_Succeeds() throws Exception {
        String patient = IOUtils.resourceToString("/patient-1.xml", StandardCharsets.UTF_8);

        ValidationResult result = validator.validateWithResult(patient);

        for (SingleValidationMessage next : result.getMessages()) {
            System.out.println(" Next issue " + next.getSeverity() + " - " + next.getLocationString() + " - " + next.getMessage());
        }

        assertThat(result.isSuccessful(), is(true));
    }

    @Test
    public void testValidation_NoSSIN_Succeeds() throws Exception {
        String patient = IOUtils.resourceToString("/patient-2.xml", StandardCharsets.UTF_8);

        ValidationResult result = validator.validateWithResult(patient);

        for (SingleValidationMessage next : result.getMessages()) {
            System.out.println(" Next issue " + next.getSeverity() + " - " + next.getLocationString() + " - " + next.getMessage());
        }

        assertThat(result.isSuccessful(), is(true));
    }

    @Test
    public void testValidation_InternalIdentifier_Succeeds() throws Exception {
        String patient = IOUtils.resourceToString("/patient-3.xml", StandardCharsets.UTF_8);

        ValidationResult result = validator.validateWithResult(patient);

        for (SingleValidationMessage next : result.getMessages()) {
            System.out.println(" Next issue " + next.getSeverity() + " - " + next.getLocationString() + " - " + next.getMessage());
        }

        assertThat(result.isSuccessful(), is(true));
    }

    @Test
    public void testValidation_MultipleSSIN_Fails() throws Exception {
        String patient = IOUtils.resourceToString("/patient-4.xml", StandardCharsets.UTF_8);

        ValidationResult result = validator.validateWithResult(patient);

        for (SingleValidationMessage next : result.getMessages()) {
            System.out.println(" Next issue " + next.getSeverity() + " - " + next.getLocationString() + " - " + next.getMessage());
        }

        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void testValidation_MultipleIdentifiers_Succeeds() throws Exception {
        String patient = IOUtils.resourceToString("/patient-5.xml", StandardCharsets.UTF_8);

        ValidationResult result = validator.validateWithResult(patient);

        for (SingleValidationMessage next : result.getMessages()) {
            System.out.println(" Next issue " + next.getSeverity() + " - " + next.getLocationString() + " - " + next.getMessage());
        }

        assertThat(result.isSuccessful(), is(true));
    }

    @Test
    public void testValidation_UseNotSpecified_Fails() throws Exception {
        String patient = IOUtils.resourceToString("/patient-6.xml", StandardCharsets.UTF_8);

        ValidationResult result = validator.validateWithResult(patient);

        for (SingleValidationMessage next : result.getMessages()) {
            System.out.println(" Next issue " + next.getSeverity() + " - " + next.getLocationString() + " - " + next.getMessage());
        }

        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void testValidation_UseWrongValue_Fails() throws Exception {
        String patient = IOUtils.resourceToString("/patient-7.xml", StandardCharsets.UTF_8);

        ValidationResult result = validator.validateWithResult(patient);

        for (SingleValidationMessage next : result.getMessages()) {
            System.out.println(" Next issue " + next.getSeverity() + " - " + next.getLocationString() + " - " + next.getMessage());
        }

        assertThat(result.isSuccessful(), is(false));
    }



}
