
package com.product.credit.data.factory;

import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class RestrictionDataFactory {

    private static final Faker faker = new Faker();
    private static final Logger log = LogManager.getLogger(RestrictionDataFactory.class);

    private RestrictionDataFactory() {
    }

    public static String cpfWithoutRestriction() {
        String cpf = String.valueOf(faker.number().randomNumber(11, false));

        log.info("CPF without restriction in use: {}", cpf);
        return cpf;
    }

    public static String cpfWithRestriction() {
        String cpfWithRestriction = faker.options().option("97093236014", "60094146012", "84809766080",
                "62648716050", "26276298085", "01317496094", "55856777050", "19626829001", "24094592008",
                "58063164083");

        log.info("CPF with restriction in use: {}", cpfWithRestriction);
        return cpfWithRestriction;
    }
}
