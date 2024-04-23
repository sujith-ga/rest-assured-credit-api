package com.product.credit.specs;

import com.product.credit.data.factory.RestrictionDataFactory;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

public final class RestrictionsSpecs {

    private RestrictionsSpecs() {
    }

    public static RequestSpecification cpfWithoutRestrictionRequestSpec() {

        return new RequestSpecBuilder().
            addRequestSpecification(InitialStateSpecs.set()).
            addPathParam("cpf", RestrictionDataFactory.cpfWithoutRestriction()).build();
    }

    public static ResponseSpecification notFoundResponse() {
        return new ResponseSpecBuilder().expectStatusCode(HttpStatus.SC_NOT_FOUND).build();
    }

}
