package com.sisa.tabata.ui.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.google.inject.Singleton;
import com.sisa.tabata.ui.domain.Size;

/**
 * Created by Laca on 2015.02.20..
 */
@Singleton
public class CircularProgressBarDrawable extends Drawable {

    private static final int START_ANGLE = -90;
    private static final int FULL_ROTATION = 360;
    private static final int STROKE_WIDTH_PERCENTAGE = 7;

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

    public CircularProgressBarDrawable() {
        setUpProgressPaint();
        setUpInnerCirclePaint();
        setUpBackgroundPaint();
    }

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

    public void setContainerSize(Size containerSize) {
        containerViewWidth = containerSize.getWidth();
        containerViewHeight = containerSize.getHeight();
        int strokeWidth = Math.round((float) containerViewWidth / 100 * STROKE_WIDTH_PERCENTAGE);
        int padding = Double.valueOf(Math.min(containerViewWidth, containerViewHeight) * 0.04).intValue();
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

    private void setUpProgressPaint() {
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.SQUARE);
        progressPaint.setColor(Color.CYAN);
    }

    private void setUpInnerCirclePaint() {
        circularPaint = new Paint();
        circularPaint.setAntiAlias(true);
        circularPaint.setStyle(Paint.Style.STROKE);
        circularPaint.setColor(Color.WHITE);
    }

    private void setUpBackgroundPaint() {
        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(Color.BLACK);
    }

    public void setMaxValue(long maxValue) {
        this.maxValue = maxValue;
    }

    public void setBackgroundPaintColor(int color){
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
