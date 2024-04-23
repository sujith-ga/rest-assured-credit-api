
package com.product.credit.client;

import com.product.credit.data.changeless.RestrictionsData;
import com.product.credit.specs.RestrictionsSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestrictionsClient {

    public Response queryRestrictionAndReturnNotFound() {
        return
            given().
                spec(RestrictionsSpecs.cpfWithoutRestrictionRequestSpec()).
            when().
                get(RestrictionsData.GET_RESTRICTIONS).
            then().
                spec(RestrictionsSpecs.notFoundResponse()).
                extract().
                    response();
    }
}
