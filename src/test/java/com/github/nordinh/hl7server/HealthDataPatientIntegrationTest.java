package com.github.nordinh.hl7server;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "com.github.nordinh")
public class HealthDataPatientIntegrationTest {

    @LocalServerPort
    int randomPort;

    @Test
    public void createPatientUsingFhirEndpoint_succeeds() throws IOException {
        String patient = IOUtils.resourceToString("/patient-1.xml", Charset.defaultCharset());

        String response = given()
            .port(randomPort)
            .body(patient)
            .contentType("application/fhir+xml")
            .when()
            .post("/fhir/Patient")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .body().asString();

        assertThat(response).contains("12345678901");
    }

}
