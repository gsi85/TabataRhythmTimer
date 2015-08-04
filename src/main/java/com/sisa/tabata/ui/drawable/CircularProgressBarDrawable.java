package com.sisa.tabata.ui.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.google.inject.Inject;
import com.sisa.tabata.ui.domain.Size;

import roboguice.inject.ContextSingleton;

/**
 * Circular progress bar drawable class.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class CircularProgressBarDrawable extends Drawable {

    private static final int START_ANGLE = -90;
    private static final int FULL_ROTATION = 360;
    private static final int STROKE_WIDTH_PERCENTAGE = 7;
    private static final int HUNDRED_PERCENT = 100;
    private static final double PADDING_MULTIPLIER = 0.04;

    private long maxValue;
    private int sweepAngle;
    private int containerViewWidth;
    private int containerViewHeight;
    private int rectTop;
    private int rectLeft;
    private int rectBottom;
    private int rectRight;
    private int innerCircleRadius;
    private Paint progressPaint;
    private Paint circularPaint;
    private Paint backgroundPaint;
    private PaintFactory paintFactory = new PaintFactory();

    /**
     * DI constructor.
     */
    public CircularProgressBarDrawable() {
        progressPaint = paintFactory.createPaint(true, Paint.Style.STROKE, Paint.Cap.SQUARE, Color.CYAN);
        circularPaint = paintFactory.createPaint(true, Paint.Style.STROKE, Paint.Cap.BUTT, Color.WHITE);
        backgroundPaint = paintFactory.createPaint(true, Paint.Style.FILL, Paint.Cap.BUTT, Color.BLACK);
    }

    /**
     * Updates the progress bar
     *
     * @param remaining remaining amount from countdown
     */
    public void update(long remaining) {
        sweepAngle = calculateNewSweepAngle(remaining);
        invalidateSelf();
    }

    @Override
    public void draw(Canvas canvas) {
        drawInnerCircle(canvas);
        drawProgressBar(canvas);
        drawBackground(canvas);
    }

    /**
     * Sets the size of container of the progress bar.
     *
     * @param containerSize {@link Size}
     */
    public void setContainerSize(Size containerSize) {
        containerViewWidth = containerSize.getWidth();
        containerViewHeight = containerSize.getHeight();
        int strokeWidth = Math.round((float) containerViewWidth / HUNDRED_PERCENT * STROKE_WIDTH_PERCENTAGE);
        int padding = Double.valueOf(Math.min(containerViewWidth, containerViewHeight) * PADDING_MULTIPLIER).intValue();
        int halfWidth = containerViewWidth / 2;
        int halfHeight = containerViewHeight / 2;
        int radius = Math.min(containerViewWidth, containerViewHeight) / 2;
        innerCircleRadius = radius - padding - (strokeWidth / 2);
        rectLeft = halfWidth - radius + padding;
        rectTop = halfHeight - radius + padding;
        rectRight = halfWidth + radius - padding;
        rectBottom = halfHeight + radius - padding;
        progressPaint.setStrokeWidth(strokeWidth);
    }

    private int calculateNewSweepAngle(long remaining) {
        return Math.round((float) (maxValue - remaining) / maxValue * FULL_ROTATION);
    }

    private void drawInnerCircle(Canvas canvas) {
        canvas.drawCircle(containerViewWidth / 2, containerViewHeight / 2, innerCircleRadius, circularPaint);
    }

    private void drawProgressBar(Canvas canvas) {
        RectF rectangleToFit = new RectF(rectLeft, rectTop, rectRight, rectBottom);
        canvas.drawArc(rectangleToFit, START_ANGLE, sweepAngle, false, progressPaint);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawCircle(containerViewWidth / 2, containerViewHeight / 2, innerCircleRadius - 1, backgroundPaint);
    }

    public void setMaxValue(long maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Sets the background color.
     *
     * @param color the colors code
     */
    public void setBackgroundPaintColor(int color) {
        backgroundPaint.setColor(color);
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
