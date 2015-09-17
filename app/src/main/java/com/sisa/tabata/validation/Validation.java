package com.sisa.tabata.validation;

import java.util.Collection;
import java.util.Map;

/**
 * Validation class evaluating if the given object is not null and has content.
 *
 * @author Laca
 */
public final class Validation {

    private Validation() {
    }

    /**
     * Validates if the given object is not null.
     * If the {@code object} is a {@link Map} or {@link Collection} validates it has elements.
     *
     * @param object the object to validate
     * @return true if not empty
     */
    public static boolean notEmpty(Object object) {
        return !empty(object);
    }

    /**
     * Validates if the given {@code object} is null.
     * If the {@code object} is a {@link Map} or {@link Collection} validates it has no elements.
     *
     * @param object the object to validate
     * @return true if empty
     */
    public static boolean empty(final Object object) {
        boolean empty = false;
        if (object == null) {
            empty = true;
        } else if (object instanceof Map) {
            empty = ((Map) object).isEmpty();
        } else if (object instanceof Collection) {
            empty = ((Collection) object).isEmpty();
        }
        return empty;
    }

}
