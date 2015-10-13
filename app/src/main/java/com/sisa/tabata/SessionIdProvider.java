package com.sisa.tabata;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Provider for session id, used as a unique identifier for the span of the application's lifecycle.
 *
 * @author Laszlo Sisa
 */
public final class SessionIdProvider {

    public static final String SESSION_ID;

    private static final int NUMBER_OF_BITS = 130;
    private static final int RADIX = 32;

    static {
        SecureRandom random = new SecureRandom();
        SESSION_ID = new BigInteger(NUMBER_OF_BITS, random).toString(RADIX);
    }

    private SessionIdProvider() {

    }

}
