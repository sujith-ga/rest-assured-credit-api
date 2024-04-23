
package com.product.credit.simulations;

import com.product.credit.BaseAPI;
import com.product.credit.commons.MessageFormat;
import com.product.credit.data.factory.SimulationDataFactory;
import com.product.credit.data.provider.SimulationDataProvider;
import com.product.credit.model.Simulation;
import com.product.credit.data.changeless.TestSuiteTags;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class SimulationsFunctionalTest extends BaseAPI {


    @Test
    @Tag(TestSuiteTags.FUNCTIONAL)
    @DisplayName("Should validate one existing simulation")
    void getOneExistingSimulation() {
        var existingSimulation = SimulationDataFactory.oneExistingSimulation();

        given().
            pathParam("cpf", existingSimulation.getCpf()).
        when().
            get("/simulations/{cpf}").
        then().
            statusCode(SC_OK).
            body(
                "name", equalTo(existingSimulation.getName()),
                "cpf", equalTo(existingSimulation.getCpf()),
                "email", equalTo(existingSimulation.getEmail()),
                "amount", equalTo(existingSimulation.getAmount()),
                "installments", equalTo(existingSimulation.getInstallments()),
                "insurance", equalTo(existingSimulation.getInsurance())
            );
    }

    @Test
    @Tag(TestSuiteTags.FUNCTIONAL)
    @DisplayName("Should validate all existing simulations")
    void getAllExistingSimulations() {
        var existingSimulations = SimulationDataFactory.allExistingSimulations();

        var simulationsRequested =
            when().
                get("/simulations/").
            then().
                statusCode(SC_OK).
                extract().
                   as(Simulation[].class);

        Assertions.assertThat(existingSimulations).contains(simulationsRequested);
    }

    @Test
    @Tag(TestSuiteTags.FUNCTIONAL)
    @DisplayName("Should filter by name a non-existing simulation")
    void simulationByNameNotFound() {
        given().
            queryParam("name", SimulationDataFactory.nonExistentName()).
        when().
            get("/simulations/").
        then().
            statusCode(SC_NOT_FOUND);
    }

    @Test
    @Tag(TestSuiteTags.FUNCTIONAL)
    @DisplayName("Should find a simulation filtered by name")
    void returnSimulationByName() {
        var existingSimulation = SimulationDataFactory.oneExistingSimulation();

        given().
            queryParam("name", existingSimulation.getName()).
        when().
            get("/simulations/").
        then().
            statusCode(SC_OK).
            body(
                "[0].name", equalTo(existingSimulation.getName()),
                "[0].cpf", equalTo(existingSimulation.getCpf()),
                "[0].email", equalTo(existingSimulation.getEmail()),
                "[0].amount", equalTo(existingSimulation.getAmount()),
                "[0].installments", equalTo(existingSimulation.getInstallments()),
                "[0].insurance", equalTo(existingSimulation.getInsurance())
            );
    }

    /*
     * here there is a header validation
     */
    @Test
    @Tag(TestSuiteTags.FUNCTIONAL)
    @DisplayName("Should create a new simulation")
    void createNewSimulationSuccessfully() {
        var simulation = SimulationDataFactory.validSimulation();

        given().
            contentType(ContentType.JSON).
            body(simulation).
        when().
            post("/simulations/").
        then().
            statusCode(SC_CREATED).
            header("Location", containsString(MessageFormat.locationURLByEnvironment()));
    }

    @Tag(TestSuiteTags.FUNCTIONAL)
    @ParameterizedTest(name = "Scenario: {2}")
    @ArgumentsSource(SimulationDataProvider.class)
    @DisplayName("Should validate all the invalid scenarios")
    void invalidSimulations(Simulation invalidSimulation, String path, String validationMessage) {
        given().
            contentType(ContentType.JSON).
            body(invalidSimulation).
        when().
            post("/simulations/").
        then().
            statusCode(SC_UNPROCESSABLE_ENTITY).
            body(path, is(validationMessage));
    }

    @Test
    @Tag(TestSuiteTags.FUNCTIONAL)
    @DisplayName("Should validate an CFP duplication")
    void simulationWithDuplicatedCpf() {
        var existingSimulation = SimulationDataFactory.oneExistingSimulation();
        given().
            contentType(ContentType.JSON).
            body(existingSimulation).
        when().
            post("/simulations/").
        then().
            statusCode(SC_CONFLICT).
            body("message", is("CPF already exists"));
    }

    @Test
    @Tag(TestSuiteTags.FUNCTIONAL)
    @DisplayName("Should delete an existing simulation")
    void deleteSimulationSuccessfully() {
        var existingSimulation = SimulationDataFactory.oneExistingSimulation();

        given().
            pathParam("cpf", existingSimulation.getCpf()).
        when().
            delete("/simulations/{cpf}").
        then().
            statusCode(SC_NO_CONTENT);
    }

    @Test
    @Tag(TestSuiteTags.FUNCTIONAL)
    @DisplayName("Should validate the return when a non-existent simulation is sent")
    void notFoundWhenDeleteSimulation() {
        given().
            pathParam("cpf", SimulationDataFactory.notExistentCpf()).
        when().
            delete("/simulations/{cpf}").
        then().
            statusCode(SC_NOT_FOUND);
    }

    @Test
    @Tag(TestSuiteTags.FUNCTIONAL)
    @DisplayName("Should update an existing simulation")
    void changeSimulationSuccessfully() {
        var existingSimulation = SimulationDataFactory.oneExistingSimulation();

        var simulation = SimulationDataFactory.validSimulation();
        simulation.setCpf(existingSimulation.getCpf());
        simulation.setInsurance(existingSimulation.getInsurance());

        var simulationReturned =
            given().
                contentType(ContentType.JSON).
                pathParam("cpf", existingSimulation.getCpf()).
                body(simulation).
            when().
                put("/simulations/{cpf}").
            then().
                statusCode(SC_OK).
                extract().
                    as(Simulation.class);

        assertThat("Simulation are not the same",
            simulationReturned, is(simulation));
    }

    @Test
    @Tag(TestSuiteTags.FUNCTIONAL)
    @DisplayName("Should validate the return of an update for a non-existent CPF")
    void changeSimulationCpfNotFound() {
        var simulation = SimulationDataFactory.validSimulation();

        given().
            contentType(ContentType.JSON).
            pathParam("cpf", SimulationDataFactory.notExistentCpf()).
            body(simulation).
        when().
            put("/simulations/{cpf}").
        then().
            statusCode(SC_NOT_FOUND);
    }
}
