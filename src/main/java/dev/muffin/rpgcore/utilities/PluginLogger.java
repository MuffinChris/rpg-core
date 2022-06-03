package dev.muffin.rpgcore.utilities;

import java.util.logging.Logger;

/**
 * Logger singleton class
 */
public class PluginLogger {

    private static Logger logger;

    /**
     * Get plugin specific logger
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        PluginLogger.logger = logger;
    }

}
