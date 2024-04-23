
package com.product.credit.client;

import com.product.credit.data.changeless.SimulationData;
import com.product.credit.specs.SimulationsSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class SimulationsClient {

    public Response submitSuccessfulSimulation() {
        return
            given().
                spec(SimulationsSpecs.postValidSimulation()).
            when().
                post(SimulationData.SERVICE).
            then().
                spec(SimulationsSpecs.createdSimulation()).
                extract().
                    response();
    }
}
