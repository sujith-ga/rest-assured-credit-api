
package com.product.credit.simulations;

import com.product.credit.BaseAPI;
import com.product.credit.data.factory.SimulationDataFactory;
import com.product.credit.data.changeless.TestSuiteTags;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class SimulationsContractTest extends BaseAPI {

    @Test
    @Tag(TestSuiteTags.CONTRACT)
    @DisplayName("Should validate the simulation schema for GET method")
    void getOneSimulation() {
        String existentCpf = SimulationDataFactory.oneExistingSimulation().getCpf();
        given().
            pathParam("cpf", existentCpf).
        when().
            get("/simulations/{cpf}").
        then().
            body(matchesJsonSchemaInClasspath("schemas/simulations_v1_schema.json"));
    }

    @Test
    @Tag(TestSuiteTags.CONTRACT)
    @DisplayName("Should validate the simulation schema for non-existing simulation")
    void simulationNotFound() {
        given().
            pathParam("cpf", SimulationDataFactory.notExistentCpf()).
        when().
            get("/simulations/{cpf}").
        then().
            body(matchesJsonSchemaInClasspath("schemas/simulations_not_existent_v1_schema.json"));
    }

    @Test
    @Tag(TestSuiteTags.CONTRACT)
    @DisplayName("Should validate the simulation schema for missing information")
    void simulationWithMissingInformation() {
        given().
            contentType(ContentType.JSON).
            body(SimulationDataFactory.missingAllInformation()).
        when().
            post("/simulations/").
        then().
            body(matchesJsonSchemaInClasspath("schemas/simulations_missing_info_v1_schema.json"));
    }
}
