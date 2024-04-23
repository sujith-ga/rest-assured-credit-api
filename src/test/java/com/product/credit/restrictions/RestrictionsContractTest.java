package com.product.credit.restrictions;

import com.product.credit.BaseAPI;
import com.product.credit.data.changeless.RestrictionsData;
import com.product.credit.data.factory.RestrictionDataFactory;
import com.product.credit.data.changeless.TestSuiteTags;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class RestrictionsContractTest extends BaseAPI {

    @Test
    @Tag(TestSuiteTags.CONTRACT)
    @DisplayName("Should validate the restrictions schema for GET method in v1")
    void contractOnV1() {
        given().
            pathParam("cpf", RestrictionDataFactory.cpfWithRestriction()).
        when().
            get(RestrictionsData.GET_RESTRICTIONS).
        then().
            body(matchesJsonSchemaInClasspath("schemas/restrictions_v1_schema.json"));
    }


    @Test
    @Tag(TestSuiteTags.CONTRACT)
    @Disabled("disabled to execute the v1")
    @DisplayName("Should validate the restrictions schema for GET method in v2")
    void contractOnV2() {
        basePath = "/api/v2";

        given().
            pathParam("cpf", RestrictionDataFactory.cpfWithRestriction()).
        when().
            get(RestrictionsData.GET_RESTRICTIONS).
        then().
            body(matchesJsonSchemaInClasspath("schemas/restrictions_v1_schema.json"));
    }
}
