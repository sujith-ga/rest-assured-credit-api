
package com.product.credit.data.factory;

import com.product.credit.model.Simulation;
import com.product.credit.model.SimulationBuilder;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;

import static io.restassured.RestAssured.when;

public final class SimulationDataFactory {

    private static final Logger log = LogManager.getLogger(SimulationDataFactory.class);

    private static final int MIN_AMOUNT = 1000;
    private static final int MAX_AMOUNT = 40000;
    private static final int MIN_INSTALLMENTS = 2;
    private static final int MAX_INSTALLMENTS = 48;
    private static final Faker faker = new Faker();

    private SimulationDataFactory() {
    }

    public static String nonExistentName() {
        String nonExistentName = faker.name().firstName();

        log.info("Non existent name in use: {}", nonExistentName);
        return nonExistentName;
    }

    public static String notExistentCpf() {
        String nonExistentCpf = faker.cpf().valid();

        log.info("Not existent CPF in use: {}", nonExistentCpf);
        return nonExistentCpf;
    }

    public static Simulation validSimulation() {
        return newSimulation();
    }

    public static Simulation oneExistingSimulation() {
        var simulations = allSimulationsFromApi();
        Simulation oneExistingSimulation = simulations.get(new SecureRandom().nextInt(simulations.size()));

        log.info(oneExistingSimulation);
        return oneExistingSimulation;
    }

    public static List<Simulation> allExistingSimulations() {
        return allSimulationsFromApi();
    }

    public static Simulation simulationLessThanMinAmount() {
        var simulationLessThanMinAmount = validSimulation();
        simulationLessThanMinAmount.setAmount(new BigDecimal(faker.number().numberBetween(1, MIN_AMOUNT - 1)));

        log.info(simulationLessThanMinAmount);
        return simulationLessThanMinAmount;
    }

    public static Simulation simulationExceedAmount() {
        var simulationExceedAmount = validSimulation();
        simulationExceedAmount.setAmount(new BigDecimal(faker.number().numberBetween(MAX_AMOUNT + 1, 99999)));

        log.info(simulationExceedAmount);
        return simulationExceedAmount;
    }

    public static Simulation simulationLessThanMinInstallments() {
        var simulationLessThanMinInstallments = validSimulation();
        simulationLessThanMinInstallments.setInstallments(MIN_INSTALLMENTS - 1);

        log.info(simulationLessThanMinInstallments);
        return simulationLessThanMinInstallments;
    }

    public static Simulation simulationExceedInstallments() {
        var simulationExceedInstallments = validSimulation();
        simulationExceedInstallments.setInstallments(faker.number().numberBetween(MAX_INSTALLMENTS + 1, 999));

        log.info(simulationExceedInstallments);
        return simulationExceedInstallments;
    }

    public static Simulation simulationWithNotValidEmail() {
        var simulationWithNotValidEmail = validSimulation();
        simulationWithNotValidEmail.setEmail(faker.internet().username() + "@" + "something");

        log.info(simulationWithNotValidEmail);
        return simulationWithNotValidEmail;
    }

    public static Simulation simulationWithEmptyName() {
        var simulationWithEmptyName = validSimulation();
        simulationWithEmptyName.setName(StringUtils.EMPTY);

        log.info(simulationWithEmptyName);
        return simulationWithEmptyName;
    }

    public static Simulation missingAllInformation() {
        var simulationWithMissingInfo =
                new SimulationBuilder().
                        cpf(faker.cpf().valid()).
                        name(StringUtils.EMPTY).
                        email(faker.internet().username()).
                        amount(new BigDecimal(faker.number().numberBetween(1, MIN_AMOUNT - 1))).
                        installments(MIN_INSTALLMENTS - 1).
                        insurance(faker.bool().bool()).
                        build();

        log.info(simulationWithMissingInfo);
        return simulationWithMissingInfo;
    }

    private static List<Simulation> allSimulationsFromApi() {
        var simulations =
                when().
                        get("/simulations/").
                        then().
                        statusCode(HttpStatus.SC_OK).
                        extract().
                        as(Simulation[].class);

        log.info(simulations);
        return List.of(simulations);
    }

    private static Simulation newSimulation() {
        var newSimulation =
                new SimulationBuilder().
                        name(faker.name().nameWithMiddle()).
                        cpf(faker.cpf().valid()).
                        email(faker.internet().emailAddress()).
                        amount(new BigDecimal(faker.number().numberBetween(MIN_AMOUNT, MAX_AMOUNT))).
                        installments(faker.number().numberBetween(MIN_INSTALLMENTS, MAX_INSTALLMENTS)).
                        insurance(faker.bool().bool()).build();

        log.info(newSimulation);
        return newSimulation;
    }
}
