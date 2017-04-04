package com.shades.shade.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.shades.shade.R;
import com.shades.shade.model.CheckInDataSet;

import java.util.ArrayList;

public class CheckInGraphView extends View {

    private Paint paint = new Paint();
    private String lineColor = "#EAEAEA";
    private int noOfLine = 5;
    private int noOfDataCl = 12;


    private int padding;
    private int lineWidth;
    private int ringRd;
    private int ringStrokeWd;

    private ArrayList<CheckInDataSet> listOfData = null;

    public CheckInGraphView(Context context) {
        super(context);
        padding = convertDpToPixel(25);
        lineWidth = convertDpToPixel(1);
        ringRd = convertDpToPixel(6);
        ringStrokeWd = convertDpToPixel(2);
    }

    public CheckInGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        padding = convertDpToPixel(25);
        lineWidth = convertDpToPixel(1);
        ringRd = convertDpToPixel(6);
        ringStrokeWd = convertDpToPixel(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int W = getWidth();
        int H = getHeight();

        int L_X = padding;
        int T_Y = padding;
        int R_X = W - padding;
        int B_Y = H - padding;

        //Draw  the y Axis Line
        paint.setColor(Color.GRAY);
        int lineGpY = (H - padding * 2) / noOfLine;
        for (int j = 0; j < noOfLine + 1; j++) {
            paint.setStrokeWidth(lineWidth);
            canvas.drawLine(L_X, T_Y + (lineGpY * j), R_X, T_Y + (lineGpY * j), paint);
        }

        //Draw Max and Min Smiley
        paint.setColor(Color.BLACK);
        Bitmap maxIcon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.emojiscale_max);
        canvas.drawBitmap(maxIcon, R_X - maxIcon.getWidth() / 2, T_Y + lineWidth - (maxIcon.getHeight() / 2), paint);
        Bitmap minIcon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.emojiscale_min);
        canvas.drawBitmap(maxIcon, R_X - minIcon.getWidth() / 2, B_Y - lineGpY + lineWidth - (minIcon.getHeight() / 2), paint);

        //Test Only
//        listOfData = createDate();

        //Draw date wise point
        if (listOfData != null && listOfData.size() >= 1) {
            int lineGpX = (W - padding * 2) / noOfDataCl;

            //Draw Line from point to point
            Paint paintConnectedLine = new Paint();
            paintConnectedLine.setStyle(Paint.Style.STROKE);
            paintConnectedLine.setStrokeWidth(ringStrokeWd);
            paintConnectedLine.setColor(Color.parseColor("#A07DE6"));

            for (int j = 0; j < listOfData.size(); j++) {
                CheckInDataSet dataSet = listOfData.get(j);
                if (j != 0 && ((listOfData.get(j - 1).getSmileyState() != -1) && (listOfData.get(j).getSmileyState() != -1))) {
                    canvas.drawLine(L_X + (lineGpX * (j - 1)), B_Y - (lineGpY * (listOfData.get(j - 1).getSmileyState() - 1)) - lineGpY,
                            L_X + (lineGpX * j), B_Y - (lineGpY * (dataSet.getSmileyState() - 1)) - lineGpY, paintConnectedLine);
                }
            }

            //Draw Circle Point
            Paint paintRing = new Paint();
            paintRing.setStyle(Paint.Style.STROKE);
            paintRing.setStrokeWidth(ringStrokeWd);
            paintRing.setColor(Color.parseColor("#A07DE6"));

            Paint paintRingSL = new Paint();
            paintRingSL.setColor(Color.WHITE);

            for (int j = 0; j < listOfData.size(); j++) {
                CheckInDataSet dataSet = listOfData.get(j);
                if (dataSet.getSmileyState() != -1) {
                    canvas.drawCircle(L_X + (lineGpX * j), B_Y - (lineGpY * (dataSet.getSmileyState() - 1)) - lineGpY + lineWidth, ringRd, paintRingSL);
                    canvas.drawCircle(L_X + (lineGpX * j), B_Y - (lineGpY * (dataSet.getSmileyState() - 1)) - lineGpY + lineWidth, ringRd, paintRing);
                }
            }

            //Draw Indicator
            int indicatorLineH = convertDpToPixel(3);
            int indicatorLineHMx = convertDpToPixel(4);
            Paint paintIndicator = new Paint();
            paintIndicator.setColor(Color.GRAY);
            for (int i = 0; i < 12; i++) {
                if (i == 0 || ((i + 1) % 5) == 0) {
                    paintIndicator.setStrokeWidth(lineWidth + 2);
                    canvas.drawLine(L_X + (lineGpX * i), B_Y + indicatorLineHMx, L_X + (lineGpX * i), B_Y - indicatorLineHMx, paintIndicator);

                    if (listOfData.size() > i) {
                        drawDate(canvas, listOfData.get(i).getDate(), L_X + (lineGpX * i), B_Y);
                    }
                } else {
                    paintIndicator.setStrokeWidth(lineWidth);
                    canvas.drawLine(L_X + (lineGpX * i), B_Y + indicatorLineH, L_X + (lineGpX * i), B_Y - indicatorLineH, paintIndicator);
                }
            }

            Bitmap minArrow = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.today_marker);
            canvas.drawBitmap(minArrow, L_X + (lineGpX * 11) - minIcon.getWidth() / 4, B_Y + (minIcon.getHeight() / 2), paint);
        }

    }

    private void drawDate(Canvas canvas, String date, int x, int y) {
        String boundText = getShortDate(date);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(convertDpToPixel(12));

        Rect bounds = new Rect();
        paint.getTextBounds(boundText, 0, 1, bounds);
        int offTPadding = convertDpToPixel(4);
        canvas.drawText(boundText, x, y + bounds.height() + offTPadding, paint);
    }

    public void setDataSet(ArrayList<CheckInDataSet> listOfData) {
        this.listOfData = listOfData;
        invalidate();
    }


    public int convertDpToPixel(float dp) {
        Resources resources = getContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }

    public int convertPixelsToDp(float px) {
        Resources resources = getContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) dp;
    }

    private String getShortDate(String date) {
        try {
            String[] spl = date.split("-");
            if (spl.length >= 3) {
                return spl[0] + "/" + spl[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static ArrayList<CheckInDataSet> createDate() {
        ArrayList<CheckInDataSet> listOfData = new ArrayList<>();
        listOfData.add(new CheckInDataSet(1, "02-04-2017"));
        listOfData.add(new CheckInDataSet(2, "03-04-2017"));
        listOfData.add(new CheckInDataSet(3, "04-04-2017"));
        listOfData.add(new CheckInDataSet(2, "05-04-2017"));
        listOfData.add(new CheckInDataSet(-1, "06-04-2017"));
        listOfData.add(new CheckInDataSet(3, "07-04-2017"));
        listOfData.add(new CheckInDataSet(2, "08-04-2017"));
        listOfData.add(new CheckInDataSet(2, "09-04-2017"));
        listOfData.add(new CheckInDataSet(4, "10-04-2017"));
        listOfData.add(new CheckInDataSet(-1, "11-04-2017"));
        listOfData.add(new CheckInDataSet(2, "12-04-2017"));
        listOfData.add(new CheckInDataSet(4, "13-04-2017"));
        return listOfData;
    }
}
