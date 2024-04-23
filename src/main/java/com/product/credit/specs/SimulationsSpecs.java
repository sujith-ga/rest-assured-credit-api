package com.product.credit.specs;

import static org.hamcrest.CoreMatchers.startsWith;

import com.product.credit.commons.MessageFormat;
import com.product.credit.data.factory.SimulationDataFactory;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

public final class SimulationsSpecs {

    private SimulationsSpecs() {}

    public static RequestSpecification postValidSimulation() {
        var validSimulation = SimulationDataFactory.validSimulation();

        return new RequestSpecBuilder().
            addRequestSpecification(InitialStateSpecs.set()).
            setContentType(ContentType.JSON).
            setBody(validSimulation).
            build();
    }

    public static ResponseSpecification createdSimulation() {
        return new ResponseSpecBuilder().
            expectStatusCode(HttpStatus.SC_CREATED).
            expectHeader("Location", startsWith(MessageFormat.locationURLByEnvironment())).
            build();
    }
}
