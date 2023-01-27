package org.infa.util;

import java.util.function.Supplier;
import java.util.logging.Logger;

public final class LogUtil {

    private static final Logger LOGGER = Logger.getLogger(LogUtil.class.getName());
    public static final String INFO = "INFO";
    public static final String ERROR = "ERROR";
    public static final String DEBUG = "DEBUG";
    public static final String WARN = "WARN";
    public static final String TRACE = "TRACE";
    public static final String FATAL = "FATAL";

    public static void log(String message, String type) {
        switch (type) {
            case ERROR:
            case FATAL:
                LOGGER.severe(message);
                break;
            case DEBUG:
                LOGGER.fine(message);
                break;
            case WARN:
                LOGGER.warning(message);
                break;
            case TRACE:
                LOGGER.finest(message);
                break;
            default:
                LOGGER.info(message);
                break;
        }
    }

    public static void log(String type, Supplier<String> supplier) {
        log(supplier.get(), type);
    }

    public static void log(Supplier<String> supplier, Class<?> source) {
        log("source = " + source.getName() + " " + supplier.get(), INFO);
    }
}
