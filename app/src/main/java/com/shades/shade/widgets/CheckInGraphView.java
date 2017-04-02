package com.shades.shade.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.shades.shade.R;
import com.shades.shade.model.CheckInDataSet;

import java.util.ArrayList;

public class CheckInGraphView extends View {

    private Paint paint = new Paint();
    private int padding = 80;

    private String lineColor = "#EAEAEA";
    private int lineWidth = 4;
    private int noOfLine = 5;

    private int noOfDataCl = 12;
    private int ringRd = 18;
    private int ringStrokeWd = 8;

    private ArrayList<CheckInDataSet> listOfData = null;

    public CheckInGraphView(Context context) {
        super(context);
    }

    public CheckInGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
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
        int lineGpY = (H - padding * 2) / (noOfLine - 1);
        for (int j = 0; j < noOfLine; j++) {
            canvas.drawLine(L_X, T_Y + (lineGpY * j) + lineWidth, R_X, T_Y + (lineGpY * j) + lineWidth, paint);
        }

        //Draw Max and Min Smiley
        paint.setColor(Color.BLACK);
        Bitmap maxIcon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.emojiscale_max);
        canvas.drawBitmap(maxIcon, R_X - maxIcon.getWidth() / 2, T_Y + lineWidth - (maxIcon.getHeight() / 2), paint);
        Bitmap minIcon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.emojiscale_min);
        canvas.drawBitmap(maxIcon, R_X - minIcon.getWidth() / 2, B_Y + lineWidth - (minIcon.getHeight() / 2), paint);

        //Test Only
//        listOfData = createDate();

        //Draw date wise point
        if (listOfData != null && listOfData.size() >= 1) {
            int lineGpX = (W - padding * 2) / noOfDataCl;

            //Draw Line from point to point
            for (int j = 0; j < listOfData.size(); j++) {
                CheckInDataSet dataSet = listOfData.get(j);
                if (j != 0) {
                    Paint paintConnectedLine = new Paint();
                    paintConnectedLine.setStyle(Paint.Style.STROKE);
                    paintConnectedLine.setStrokeWidth(ringStrokeWd);
                    paintConnectedLine.setColor(Color.parseColor("#A07DE6"));
                    canvas.drawLine(L_X + (lineGpX * (j - 1)), B_Y - (lineGpY * (listOfData.get(j - 1).getSmileyState() - 1)),
                            L_X + (lineGpX * j), B_Y - (lineGpY * (dataSet.getSmileyState() - 1)), paintConnectedLine);
                }
            }

            //Draw Circle Point
            for (int j = 0; j < listOfData.size(); j++) {
                CheckInDataSet dataSet = listOfData.get(j);
                Paint paintRing = new Paint();
                paintRing.setStyle(Paint.Style.STROKE);
                paintRing.setStrokeWidth(ringStrokeWd);
                paintRing.setColor(Color.parseColor("#A07DE6"));

                Paint paintRingSL = new Paint();
                paintRingSL.setColor(Color.WHITE);
                canvas.drawCircle(L_X + (lineGpX * j), B_Y - (lineGpY * (dataSet.getSmileyState() - 1)) + lineWidth, ringRd, paintRingSL);
                canvas.drawCircle(L_X + (lineGpX * j), B_Y - (lineGpY * (dataSet.getSmileyState() - 1)) + lineWidth, ringRd, paintRing);
            }
        }

    }

    public void setDataSet(ArrayList<CheckInDataSet> listOfData) {
        this.listOfData = listOfData;
        invalidate();
    }

    public static ArrayList<CheckInDataSet> createDate() {
        ArrayList<CheckInDataSet> listOfData = new ArrayList<>();
        listOfData.add(new CheckInDataSet(1, "02-04-2017"));
        listOfData.add(new CheckInDataSet(2, "02-04-2017"));
        listOfData.add(new CheckInDataSet(3, "02-04-2017"));
        listOfData.add(new CheckInDataSet(2, "02-04-2017"));
        listOfData.add(new CheckInDataSet(1, "02-04-2017"));
        listOfData.add(new CheckInDataSet(3, "02-04-2017"));
        listOfData.add(new CheckInDataSet(5, "02-04-2017"));
        listOfData.add(new CheckInDataSet(2, "02-04-2017"));
        listOfData.add(new CheckInDataSet(4, "02-04-2017"));
        return listOfData;
    }
}
