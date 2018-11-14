package com.github.nordinh.hl7server;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.dstu3.model.Enumerations;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Patient;

public class PatientResourceProvider implements IResourceProvider {

    @Override
    public Class<Patient> getResourceType() {
        return Patient.class;
    }

    @Read
    public Patient getResourceById(@IdParam IdType id) {
        Patient patient = new Patient();
        patient.addIdentifier()
                .setSystem("http://healthdata.be/ssin")
                .setValue("12345678901");
        patient.addName()
                .setFamily("Test")
                .addGiven("PatientOne");
        patient.setGender(Enumerations.AdministrativeGender.FEMALE);
        return patient;
    }
}
