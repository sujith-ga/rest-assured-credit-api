package com.product.credit.data.provider;

import com.product.credit.data.factory.SimulationDataFactory;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static com.product.credit.data.changeless.SimulationErrorsData.ERRORS_AMOUNT_GREATER;
import static com.product.credit.data.changeless.SimulationErrorsData.ERRORS_AMOUNT_LESS;
import static com.product.credit.data.changeless.SimulationErrorsData.ERRORS_EMAIL;
import static com.product.credit.data.changeless.SimulationErrorsData.ERRORS_INSTALLMENTS_GREATER;
import static com.product.credit.data.changeless.SimulationErrorsData.ERRORS_INSTALLMENTS_LESS;
import static com.product.credit.data.changeless.SimulationErrorsData.ERRORS_NAME;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SimulationDataProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {

        var simulationLessThanMinAmount = SimulationDataFactory.simulationLessThanMinAmount();
        var simulationExceedAmount = SimulationDataFactory.simulationExceedAmount();
        var simulationLessThanMinInstallments = SimulationDataFactory.simulationLessThanMinInstallments();
        var simulationExceedInstallments = SimulationDataFactory.simulationExceedInstallments();
        var simulationWithNotValidEmail = SimulationDataFactory.simulationWithNotValidEmail();
        var simulationWithEmptyName = SimulationDataFactory.simulationWithEmptyName();

        return Stream.of(
                arguments(simulationLessThanMinAmount, ERRORS_AMOUNT_GREATER.key, ERRORS_AMOUNT_GREATER.message),
                arguments(simulationExceedAmount, ERRORS_AMOUNT_LESS.key, ERRORS_AMOUNT_LESS.message),
                arguments(simulationLessThanMinInstallments, ERRORS_INSTALLMENTS_GREATER.key, ERRORS_INSTALLMENTS_GREATER.message),
                arguments(simulationExceedInstallments, ERRORS_INSTALLMENTS_LESS.key, ERRORS_INSTALLMENTS_LESS.message),
                arguments(simulationWithNotValidEmail, ERRORS_EMAIL.key, ERRORS_EMAIL.message),
                arguments(simulationWithEmptyName, ERRORS_NAME.key, ERRORS_NAME.message)
        );
    }
}
