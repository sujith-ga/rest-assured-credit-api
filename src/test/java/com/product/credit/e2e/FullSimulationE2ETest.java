package com.product.credit.e2e;

import com.product.credit.client.RestrictionsClient;
import com.product.credit.client.SimulationsClient;
import com.product.credit.data.changeless.TestSuiteTags;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

class FullSimulationE2ETest {

    private final RestrictionsClient restrictionsClient = new RestrictionsClient();
    private final SimulationsClient simulationsClient = new SimulationsClient();

    @Test
    @Tag(TestSuiteTags.E2E)
    @DisplayName("Should submit a successful simulation for a non-restricted CPF")
    void completeSimulation() {
        var notFound = restrictionsClient.queryRestrictionAndReturnNotFound();
        assertThat(notFound.getStatusCode()).isEqualTo(SC_NOT_FOUND);

        var successfulSimulation = simulationsClient.submitSuccessfulSimulation();
        assertThat(successfulSimulation.getHeader("Location")).isNotEmpty();
    }
}
