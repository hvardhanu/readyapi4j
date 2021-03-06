package com.smartbear.readyapi4j.testserver.execution;

/**
 * Holds constants with default server configuration for the Ready! API Runtime Service.
 */
public class ServerDefaults {

    public static final String VERSION_PREFIX = "/v1";
    private ServerDefaults() {
    }

    public static final String SERVICE_BASE_PATH = "/readyapi";

    public static final int DEFAULT_PORT = 8443;
    public static final Scheme DEFAULT_SCHEME = Scheme.HTTPS;
}
