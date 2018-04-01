package com.jacky.loadingbulb;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 2018/3/30.
 * github:[https://github.com/jacky1234]
 *
 * @author jackyang
 */

public class LightDrawable extends Drawable {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint lightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int radius;
    private int sweep = 300;
    private RectF rectF;
    private Path path = new Path();
    private float progress;

    public void setProgress(float progress) {
        this.progress = progress;
        invalidateSelf();
    }

    private float getBulbBottomLengthLeft() {
        return radius * 0.5f;
    }

    private float getBulbBottomLengthRight() {
        return radius * 0.4f;
    }

    private float getWhiteArcPadding() {
        return radius * 0.5f;
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawColor(Color.YELLOW);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStyle(Paint.Style.STROKE);
        whitePaint.setStrokeWidth(20);

        lightPaint.setColor(Color.RED);
        lightPaint.setStyle(Paint.Style.STROKE);
        lightPaint.setStrokeWidth(20);

        final Rect bounds = getBounds();
        radius = (int) (bounds.width() * 1.0f / 4);
        int centerX = bounds.centerX();
        int centerY = bounds.centerY();

        //1 arc
        rectF = new RectF(-radius, -radius, radius, radius);
        rectF.offset(centerX, centerY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.RED);
        path.addArc(rectF, 270 - sweep / 2, sweep);

        //draw white arc
        final RectF WhiteRectF = new RectF(rectF);
        WhiteRectF.inset(getWhiteArcPadding(), getWhiteArcPadding());
        whitePaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(WhiteRectF, -70, 50, false, whitePaint);

        //2 draw bottom of bulb
        final float delta = 0.08f * radius;

        float[] start = new float[2];
        double radians = Math.toRadians((360 - sweep) / 2);
        start[0] = (float) (-radius * Math.sin(radians)) + centerX;
        start[1] = (float) (radius * Math.cos(radians) + centerY);
        float[] end = new float[2];
        end[0] = (float) (radius * Math.sin(radians)) + centerX;
        end[1] = start[1];
        path.moveTo(start[0], start[1]);
        path.lineTo(start[0], start[1] + getBulbBottomLengthLeft() - delta);
        path.quadTo(start[0], start[1] + getBulbBottomLengthLeft(), start[0] + delta * 0.8f, start[1] + getBulbBottomLengthLeft() - delta * 0.2f);
        path.lineTo(end[0] - delta * 0.2f, end[1] + getBulbBottomLengthRight() + delta * 0.2f);
        path.quadTo(end[0], end[1] + getBulbBottomLengthRight(), end[0], end[1] + getBulbBottomLengthRight() - delta * 0.8f);
        path.lineTo(end[0], end[1]);
        canvas.drawPath(path, mPaint);

//        draw bottom line
        canvas.save();
        canvas.translate(centerX, (float) (centerY + (radius * Math.cos(Math.toRadians(360 - sweep) / 2)) + (getBulbBottomLengthRight() + getBulbBottomLengthLeft()) / 2));
        float horizonGap = (float) (2 * radius * (Math.sin(Math.toRadians(360 - sweep) / 2)));
        float verticalGap = radius * 0.2f;
        radians = Math.atan((getBulbBottomLengthLeft() - getBulbBottomLengthRight()) / (2 * radius * Math.sin(Math.toRadians(360 - sweep) / 2)));
        canvas.rotate(-1 * (float) Math.toDegrees(radians));
        canvas.drawLine(-horizonGap * 0.7f * 0.5f, verticalGap, horizonGap * 0.7f * 0.5f, verticalGap, mPaint);
        canvas.drawLine(-horizonGap * 0.4f * 0.5f, verticalGap * 2, horizonGap * 0.4f * 0.5f, verticalGap * 2, mPaint);
        canvas.restore();

        //draw light
        lightPaint.setStrokeCap(Paint.Cap.ROUND);
//        final float strokeWidth = lightPaint.getStrokeWidth();
//        final RectF light = new RectF(radius + radius * 0.25f + radius * 0.5f * progress, -strokeWidth / 2, radius + radius * 0.75f, strokeWidth / 2);
        final int save = canvas.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(210);
        for (int i = 0; i < 5; i++) {
            canvas.save();
            canvas.rotate(i * 30);
//            canvas.drawRoundRect(light, 10, 10, lightPaint);
            canvas.drawLine(radius + radius * 0.25f + radius * 0.5f * progress, 0, radius + radius * 0.75f, 0, lightPaint);
            canvas.restore();
        }
        canvas.restoreToCount(save);

    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
