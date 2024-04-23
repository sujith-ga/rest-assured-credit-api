
package com.product.credit.commons;

import com.product.credit.config.ConfigurationManager;
import com.product.credit.data.changeless.SimulationData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.text.MessageFormat.format;

public class MessageFormat {

    private static final Logger log = LogManager.getLogger(MessageFormat.class);

    private MessageFormat() {}

    public static String locationURLByEnvironment() {
        String locationURL;
        var configuration = ConfigurationManager.getConfiguration();

        locationURL = configuration.port() < 8000
                ? format("{0}{1}{2}", configuration.baseURI(), configuration.basePath(), SimulationData.SERVICE)
                : format("{0}:{1}{2}{3}", configuration.baseURI(), String.valueOf(configuration.port()), configuration.basePath(), SimulationData.SERVICE);

        log.debug(locationURL);
        return locationURL;
    }
}
