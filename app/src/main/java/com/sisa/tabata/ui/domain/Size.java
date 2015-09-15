package com.sisa.tabata.ui.domain;

/**
 * Immutable domain object representing a size of two dimensions.
 *
 * @author Laszlo Sisa
 */
public class Size {

    private final int width;
    private final int height;

    /**
     * DI constructor.
     *
     * @param width the width
     * @param height the height
     */
    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
