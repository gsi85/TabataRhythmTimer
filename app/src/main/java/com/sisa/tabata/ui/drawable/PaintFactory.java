package com.sisa.tabata.ui.drawable;

import android.graphics.Paint;

/**
 * Factory for {@link Paint}.
 *
 * @author Laszlo Sisa
 */
public class PaintFactory {

    /**
     * Creates a new, configured {@link Paint} instance.
     *
     * @param antiAlias anti alias flag
     * @param paintStyle {@link Paint.Style}
     * @param strokeCap {@link Paint.Cap}
     * @param color color code
     * @return {@link Paint}
     */
    public Paint createPaint(final boolean antiAlias, final Paint.Style paintStyle, final Paint.Cap strokeCap, final int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(antiAlias);
        paint.setStyle(paintStyle);
        paint.setStrokeCap(strokeCap);
        paint.setColor(color);
        return paint;
    }
}
