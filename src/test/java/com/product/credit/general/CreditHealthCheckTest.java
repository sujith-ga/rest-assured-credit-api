
package com.product.credit.general;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.when;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;

import com.product.credit.config.ConfigurationManager;
import com.product.credit.BaseAPI;
import com.product.credit.data.changeless.TestSuiteTags;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CreditHealthCheckTest extends BaseAPI {

    @BeforeEach
    public void setup() {
        configuration = ConfigurationManager.getConfiguration();
        basePath = configuration.health();
    }

    @AfterEach
    void tearDown() {
        basePath = configuration.basePath();
    }

    @Test
    @Tag(TestSuiteTags.HEALTH)
    @DisplayName("Should be able to hit the health endpoint")
    void healthCheck() {
        when().
            get("/health").
        then().
            statusCode(SC_OK).
            body("status", is("UP"));
    }
}
