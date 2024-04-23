package com.product.credit.restrictions;

import com.product.credit.BaseAPI;
import com.product.credit.data.changeless.RestrictionsData;
import com.product.credit.data.factory.RestrictionDataFactory;
import com.product.credit.data.changeless.TestSuiteTags;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

class RestrictionsFunctionalTest extends BaseAPI {

    @Test
    @Tag(TestSuiteTags.FUNCTIONAL)
    @DisplayName("Should query a CPF without restriction")
    void cpfWithNoRestriction() {
        given().
            pathParam(RestrictionsData.CPF, RestrictionDataFactory.cpfWithoutRestriction()).
        when().
            get(RestrictionsData.GET_RESTRICTIONS).
        then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @Tag(TestSuiteTags.FUNCTIONAL)
    @DisplayName("Should query a CPF with restriction")
    void cpfWithRestriction() {
        String cpfWithRestriction = RestrictionDataFactory.cpfWithRestriction();

        given().
            pathParam(RestrictionsData.CPF, cpfWithRestriction).
        when().
            get(RestrictionsData.GET_RESTRICTIONS).
        then()
            .statusCode(HttpStatus.SC_OK).
            body("message", is(MessageFormat.format("CPF {0} has a restriction", cpfWithRestriction)));
    }
}
