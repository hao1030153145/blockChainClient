package com.smartdatachain.api.util;

import org.web3j.crypto.LinuxSecureRandom;

import java.security.SecureRandom;

/**
 * Utility class for working with SecureRandom implementation.
 *
 * <p>This is to address issues with SecureRandom on Android. For more information, refer to the
 * following <a href="https://github.com/web3j/web3j/issues/146">issue</a>.
 */
final class SecureRandomUtils {

    private static final SecureRandom SECURE_RANDOM;

    static {
        if (isRuntime()) {
            new LinuxSecureRandom();
        }
        SECURE_RANDOM = new SecureRandom();
    }

    static SecureRandom secureRandom() {
        return SECURE_RANDOM;
    }

    private static int isRuntime = -1;

    static boolean isRuntime() {
        if (isRuntime == -1) {
            final String runtime = System.getProperty("java.runtime.name");
            isRuntime = (runtime != null && runtime.equals("isRuntime")) ? 1 : 0;
        }
        return isRuntime == 1;
    }

    private SecureRandomUtils() { }
}
