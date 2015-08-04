package com.sisa.tabata.validation;

/**
 * Validation class for assertions.
 *
 * @author Laszlo Sisa
 */
public final class Assert {

    private Assert() {

    }

    /**
     * Assert that a given object is not null.
     *
     * @param object the object to check
     * @param errorMessage error message, default is set if null
     * @throws  IllegalArgumentException if the object is null
     */
    public static void notNull(Object object, String errorMessage) {
        String defaultErrorMessage = "Argument must not be null";
        if (object == null) {
            throw new IllegalArgumentException(errorMessage != null ? errorMessage : defaultErrorMessage);
        }
    }

    /**
     * Assert that the {@code toCheck} is an instance of {@code expectedType}.
     *
     * @param expectedType the expectedType to check against
     * @param toCheck the object to check
     * @param errorMessage error message, default is set if null
     * @throws IllegalArgumentException if the classes are not assignable
     */
    public static void isInstanceOf(Class<?> expectedType, Object toCheck, String errorMessage) {
        notNull(expectedType, "Type to check must not be null");
        String defaultErrorMessage = "Object of class [" + (toCheck != null ? toCheck.getClass().getName() : "null") + "] must be an instance of "
                + expectedType;
        if (!expectedType.isInstance(toCheck)) {
            throw new IllegalArgumentException(errorMessage != null ? errorMessage : defaultErrorMessage);
        }
    }
}
