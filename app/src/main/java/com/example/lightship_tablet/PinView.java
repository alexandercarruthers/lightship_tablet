package com.example.lightship_tablet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.lightship_tablet.Models.Point;

import java.util.ArrayList;

public class PinView extends SubsamplingScaleImageView {

    private ArrayList<PointF> sPin = new ArrayList<>();
    private ArrayList<String> pinNames = new ArrayList<>();
    private ArrayList<Point> points = new ArrayList<>();
    private Bitmap pin;
    private String currentPinText;
    public PinView(Context context) {
        this(context, null);
    }

    public PinView(Context context, AttributeSet attr) {
        super(context, attr);
        initialise();
    }

    public boolean setPin(PointF sPin, String name) {
        if (pinNames.contains(name)){
            return false;
        } else {
            this.sPin.add(sPin);
            pinNames.add(name);
            Point point = new Point(sPin,name);
            points.add(point);
            currentPinText = name;
            initialise();
            invalidate();
            return true;
        }
    }

    public String getStringFromPoint(PointF coord){
        double delta = 0.0001;
        for(Point point : points) {
            boolean x = Math.abs(point.point.x-coord.x) < delta;
            boolean y = Math.abs(point.point.y-coord.y) < delta;
            if(x && y){
                return point.getTitle();
            }
        }
        return "";
    }

    public ArrayList<PointF> getPinPoints(){
        return sPin;
    }



    public PointF getPin(String name) {

        return sPin.get(pinNames.indexOf(name));
    }

    public boolean removePin(String name){
        if (pinNames.contains(name)){
            sPin.remove(pinNames.indexOf(name));
            pinNames.remove(name);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<String> getPinNames(){
        return pinNames;
    }

    private void initialise() {
        float density = getResources().getDisplayMetrics().densityDpi;
        pin = BitmapFactory.decodeResource(this.getResources(), R.drawable.forty);
        float w = (density/420f) * pin.getWidth();
        float h = (density/420f) * pin.getHeight();
        pin = Bitmap.createScaledBitmap(pin, (int)w, (int)h, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Don't draw pin before image is ready so it doesn't move around during setup.
        if (!isReady()) {
            return;
        }

        Paint paint = new Paint();
        paint.setTextSize(36);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        for (Point current_point : points){
            if (current_point != null && pin != null) {
                PointF vPin = sourceToViewCoord(current_point.point);
                float vX = vPin.x - (pin.getWidth()/2);
                float vY = vPin.y - pin.getHeight();
                canvas.drawBitmap(pin, vX, vY, paint);
                canvas.drawText(current_point.getTitle(), vX, vY, paint);
            }

        }
    }
}