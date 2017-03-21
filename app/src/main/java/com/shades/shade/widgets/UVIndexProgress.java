package com.shades.shade.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class UVIndexProgress extends View {

    private Paint paint = new Paint();
    private String pgColor = "#A07DE6";// "#48168C";
    private String pgBorderColor = "#C7C7CC";
    private float borderWidth = 4;
    private float pgIndex = 0.0f;

    public UVIndexProgress(Context context) {
        super(context);
    }

    public UVIndexProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UVIndexProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float x = getWidth();
        float y = getHeight();
        float pg = pgIndex * (x / 10);

        // FILL
        paint.setColor(Color.parseColor(pgColor));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, pg, y, paint);

        // BORDER
        paint.setStrokeWidth(borderWidth);
        paint.setColor(Color.parseColor(pgBorderColor));
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, x, y, paint);
    }

    /**
     * Set UV Index in progress
     *
     * @param pgIndex
     */
    public void setPGIndex(float pgIndex) {
        this.pgIndex = pgIndex;
        invalidate();
    }
}
