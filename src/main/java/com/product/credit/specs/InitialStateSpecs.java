package com.product.credit.specs;

import com.product.credit.config.ConfigurationManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public final class InitialStateSpecs {

    private InitialStateSpecs() {
    }

    public static RequestSpecification set() {
        var configuration = ConfigurationManager.getConfiguration();

        return new RequestSpecBuilder().
            setBaseUri(configuration.baseURI()).
            setBasePath(configuration.basePath()).
            setPort(configuration.port()).
            build();
    }
}
